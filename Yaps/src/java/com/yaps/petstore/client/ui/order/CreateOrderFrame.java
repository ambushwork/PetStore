package com.yaps.petstore.client.ui.order;

import com.yaps.petstore.common.delegate.CatalogDelegate;
import com.yaps.petstore.common.delegate.CustomerDelegate;
import com.yaps.petstore.common.delegate.OrderDelegate;
import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.dto.OrderDTO;
import com.yaps.petstore.common.dto.OrderLineDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.logging.Trace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This frame is used by the employee to create an order. To create an order the employee has
 * to first find the customer, calculate the order total amount and then create it.
 * This frame uses the OrderDelegate.
 *
 * @see OrderDelegate
 */
public final class CreateOrderFrame extends AbstractOrderFrame {

    private final JButton buttonFindCustomer = new JButton();
    private final JButton buttonCalculate = new JButton();
    private final JButton buttonCreate = new JButton();
    private final JButton buttonClear = new JButton();

    public CreateOrderFrame() {
        initComponents();
        setSize(500, 610);
        setTitle("Pet Store - Create Order");
    }

    // This method is called from within the constructor to display all the graphical components
    private void initComponents() {
        initComponents(true);

        // Panel North
        labelTitle.setText("Create Order");

        // Panel South
        panelSouth.setLayout(new GridLayout(1, 4));

        buttonFindCustomer.setText("Find Customer");
        buttonFindCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonFindCustomerActionPerformed();
            }
        });
        panelSouth.add(buttonFindCustomer);

        buttonCalculate.setText("Calculate order");
        buttonCalculate.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonCalculateActionPerformed();
            }
        });
        panelSouth.add(buttonCalculate);

        buttonCreate.setText("Create order");
        buttonCreate.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonCreateActionPerformed();
            }
        });
        panelSouth.add(buttonCreate);

        buttonClear.setText("Clear");
        buttonClear.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonClearActionPerformed();
            }
        });
        panelSouth.add(buttonClear);

        getContentPane().add(panelSouth, BorderLayout.SOUTH);
    }

    // Calling this method creates an order into the system
    private void buttonCreateActionPerformed() {
        final String mname = "buttonCreateActionPerformed";

        final OrderDTO orderDTO;
        try {
            // Sets all the Order data
            orderDTO = new OrderDTO(textFirstName.getText(), textLastName.getText(), textStreet1.getText(), textCity.getText(), textZipcode.getText(), (String) comboCountry.getSelectedItem());
            orderDTO.setStreet2(textStreet2.getText());
            orderDTO.setState((String) comboState.getSelectedItem());
            orderDTO.setCreditCardType((String) comboCCType.getSelectedItem());
            orderDTO.setCreditCardNumber(textCCNumber.getText());
            orderDTO.setCreditCardExpiryDate(textCCExpDate.getText());

            // Sets all the order items data
            final Collection orderLinesDTO = new ArrayList();
            OrderLineDTO orderLineDTO;
            String orderLineId;
            for (int i = 0; i < 5; i++) {
                orderLineId = textItemId[i].getText();
                if (!"".equals(orderLineId)) {
                    orderLineDTO = new OrderLineDTO(Integer.parseInt(textItemQuantity[i].getText()), Double.parseDouble(textItemUnitCost[i].getText()));
                    orderLineDTO.setItemId(orderLineId);
                    orderLinesDTO.add(orderLineDTO);
                }
            }
            orderDTO.setOrderLines(orderLinesDTO);
            orderDTO.setCustomerId(textCustomerId.getText());

            // Create the order
            final OrderDTO result = OrderDelegate.createOrder(orderDTO);
            textOrderId.setText(result.getId());
            textOrderDate.setText(DateFormat.getDateInstance().format(result.getOrderDate()));

        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot access the order service", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    // Calling this method calculates the order total and all sub-totals
    private void buttonCalculateActionPerformed() {
        final String mname = "buttonCalculateActionPerformed";

        final StringBuffer itemNotFound = new StringBuffer();
        String itemId, quantity;
        boolean itemToCalculate = false;
        ItemDTO itemDTO;

        try {

            // For all the items we get all the information (price, name...)
            for (int i = 0; i < 5; i++) {
                itemId = textItemId[i].getText();
                if (!"".equals(itemId)) {
                    try {
                        itemDTO = CatalogDelegate.findItem(itemId);
                        textItemName[i].setText(itemDTO.getName());
                        textItemUnitCost[i].setText(String.valueOf(itemDTO.getUnitCost()));
                        itemToCalculate = true;
                    } catch (ObjectNotFoundException e) {
                        itemNotFound.append("\n" + itemId);
                    }
                }
            }

            // Some items are not found in the database
            if (itemNotFound.length() != 0) {
                JOptionPane.showMessageDialog(this, "The following items are not found:" + itemNotFound, "Warning", JOptionPane.WARNING_MESSAGE);

            } else if (itemToCalculate) {
                // Else we calculate the total of the order and the subtotals of each row
                double total = 0, subtotal;
                for (int i = 0; i < 5; i++) {
                    quantity = textItemQuantity[i].getText();
                    if (!"".equals(quantity)) {
                        subtotal = Integer.parseInt(quantity) * Double.parseDouble(textItemUnitCost[i].getText());
                        total += subtotal;
                        textItemSubTotoal[i].setText(String.valueOf(subtotal));
                    }
                }
                textTotal.setText(String.valueOf(total));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot access the catalog service", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    // Calling this method clear all the form
    private void buttonClearActionPerformed() {
        clearFrame();
    }

    // Calling this method gets the customer information from the system
    private void buttonFindCustomerActionPerformed() {
        final String mname = "buttonFindCustomerActionPerformed";

        final CustomerDTO customerDTO;
        try {
            customerDTO = CustomerDelegate.findCustomer(textCustomerId.getText());
            textCity.setText(customerDTO.getCity());
            comboCountry.setSelectedItem(customerDTO.getCountry());
            textFirstName.setText(customerDTO.getFirstname());
            textLastName.setText(customerDTO.getLastname());
            comboState.setSelectedItem(customerDTO.getState());
            textStreet1.setText(customerDTO.getStreet1());
            textStreet2.setText(customerDTO.getStreet2());
            textZipcode.setText(customerDTO.getZipcode());
            textCCExpDate.setText(customerDTO.getCreditCardExpiryDate());
            textCCNumber.setText(customerDTO.getCreditCardNumber());
            comboCCType.setSelectedItem(customerDTO.getCreditCardType());
        } catch (ObjectNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Customer id not found", "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot access the customer service", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }
}
