package com.yaps.petstore.client.ui.order;

import com.yaps.petstore.common.delegate.OrderDelegate;
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
import java.util.Iterator;

/**
 * This frame is used by the employee to display and remove an order.
 * This frame uses the OrderDelegate.
 *
 * @see OrderDelegate
 */
public final class ManageOrderFrame extends AbstractOrderFrame {

    private final JButton buttonFindOrder = new JButton();
    private final JButton buttonDeleteOrder = new JButton();

    public ManageOrderFrame() {
        initComponents();
        setTitle("Pet Store - Manage Order");
        setSize(500, 610);
    }

    // This method is called from within the constructor to display all the graphical components
    private void initComponents() {
        initComponents(false);

        // Panel North
        labelTitle.setText("Manage Order");

        // Panel South
        panelSouth.setLayout(new GridLayout(1, 2));

        buttonFindOrder.setText("Find Order");
        buttonFindOrder.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonFindOrderActionPerformed();
            }
        });
        panelSouth.add(buttonFindOrder);

        buttonDeleteOrder.setText("Delete order");
        buttonDeleteOrder.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonDeleteOrderActionPerformed();
            }
        });
        panelSouth.add(buttonDeleteOrder);

        getContentPane().add(panelSouth, BorderLayout.SOUTH);
    }

    // By calling this method it deletes the selected order
    private void buttonDeleteOrderActionPerformed() {
        final String mname = "buttonDeleteOrderActionPerformed";

        final String orderId = textOrderId.getText();
        if ("".equals(orderId)) {
            JOptionPane.showMessageDialog(this, "You have to enter an order id ", "Delete", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Asks if we want to remove the order
        final int anwser = JOptionPane.showConfirmDialog(this, "Do you want to remove order id " + orderId, "Delete", JOptionPane.YES_NO_OPTION);
        if (anwser == JOptionPane.NO_OPTION)
            return;

        try {
            OrderDelegate.deleteOrder(orderId);
            clearFrame();
        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot access the order service", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    // By calling this method it gets all the information of the order from the database
    private void buttonFindOrderActionPerformed() {
        final String mname = "buttonFindOrderActionPerformed";

        final OrderDTO orderDTO;
        try {
            orderDTO = OrderDelegate.findOrder(textOrderId.getText());
            textOrderDate.setText(DateFormat.getInstance().format(orderDTO.getOrderDate()));
            textCustomerId.setText(orderDTO.getCustomerId());
            textFirstName.setText(orderDTO.getFirstname());
            textLastName.setText(orderDTO.getLastname());
            textStreet1.setText(orderDTO.getStreet1());
            textStreet2.setText(orderDTO.getStreet2());
            textCity.setText(orderDTO.getCity());
            comboState.setSelectedItem(orderDTO.getState());
            textZipcode.setText(orderDTO.getZipcode());
            comboCountry.setSelectedItem(orderDTO.getCountry());
            textCCNumber.setText(orderDTO.getCreditCardNumber());
            comboCCType.setSelectedItem(orderDTO.getCreditCardType());
            textCCExpDate.setText(orderDTO.getCreditCardExpiryDate());

            int i = 0, quantity;
            double unitCost, subtotal, total = 0;
            for (Iterator iterator = orderDTO.getOrderLines().iterator(); iterator.hasNext();) {
                final OrderLineDTO orderLineDTO = (OrderLineDTO) iterator.next();
                textItemId[i].setText(orderLineDTO.getItemId());
                textItemName[i].setText(orderLineDTO.getItemName());
                quantity = orderLineDTO.getQuantity();
                unitCost = orderLineDTO.getUnitCost();
                subtotal = quantity * unitCost;
                total += subtotal;
                textItemQuantity[i].setText(String.valueOf(quantity));
                textItemUnitCost[i].setText(String.valueOf(unitCost));
                textItemSubTotoal[i].setText(String.valueOf(subtotal));
                i++;
            }
            textTotal.setText(String.valueOf(total));

        } catch (ObjectNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Order id not found", "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot access the order service", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }
}
