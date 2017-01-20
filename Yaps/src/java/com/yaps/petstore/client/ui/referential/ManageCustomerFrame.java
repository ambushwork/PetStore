package com.yaps.petstore.client.ui.referential;

import com.yaps.petstore.client.ui.AbstractFrame;
import com.yaps.petstore.client.ui.referential.bar.*;
import com.yaps.petstore.common.constant.AmericanStates;
import com.yaps.petstore.common.constant.Countries;
import com.yaps.petstore.common.constant.CreditCardTypes;
import com.yaps.petstore.common.delegate.CustomerDelegate;
import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.DuplicateKeyException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.logging.Trace;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the graphical user interface that enables an employee
 * to create, find, delete and update a Customer.
 */
public final class ManageCustomerFrame extends AbstractFrame implements BarEventListener {

    // Variables declaration
    private final JLabel labelTitle = new JLabel();
    private final JPanel panelCenter = new JPanel();
    private final JTextField textId = new JTextField();
    private final JTextField textFirstname = new JTextField();
    private final JTextField textLastname = new JTextField();
    private final JTextField textTelephone = new JTextField();
    private final JTextField textEmail = new JTextField();
    private final JTextField textStreet1 = new JTextField();
    private final JTextField textStreet2 = new JTextField();
    private final JTextField textCity = new JTextField();
    private final JComboBox comboState = new JComboBox(AmericanStates.getAll());
    private final JTextField textZipcode = new JTextField();
    private final JComboBox comboCountry = new JComboBox(Countries.getAll());
    private final JTextField textCreditCardNumber = new JTextField();
    private final JComboBox comboCreditCardType = new JComboBox(CreditCardTypes.getAll());
    private final JTextField textCreditCardExpiryDate = new JTextField();

    private final ManageBar manageBar = new ManageBar();

    /**
     * Creates new form
     */
    public ManageCustomerFrame() {
        initComponents();
        pack();
        manageBar.setManageListener(this);
    }

    // This method is called from within the constructor to display all the graphical components
    private void initComponents() {
        // Panel North
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setFont(new Font("Dialog", 1, 18));
        labelTitle.setText("Customer");

        getContentPane().add(labelTitle, BorderLayout.NORTH);

        // Panel Center
        panelCenter.setLayout(new GridLayout(14, 2));

        panelCenter.add(new JLabel("Identifier"));
        panelCenter.add(textId);

        panelCenter.add(new JLabel("First Name"));
        panelCenter.add(textFirstname);

        panelCenter.add(new JLabel("Last Name"));
        panelCenter.add(textLastname);

        panelCenter.add(new JLabel("Telephone"));
        panelCenter.add(textTelephone);

        panelCenter.add(new JLabel("Email"));
        panelCenter.add(textEmail);

        panelCenter.add(new JLabel("Street 1"));
        panelCenter.add(textStreet1);

        panelCenter.add(new JLabel("Street 2"));
        panelCenter.add(textStreet2);

        panelCenter.add(new JLabel("City"));
        panelCenter.add(textCity);

        panelCenter.add(new JLabel("State"));
        panelCenter.add(comboState);

        panelCenter.add(new JLabel("Zipcode"));
        panelCenter.add(textZipcode);

        panelCenter.add(new JLabel("Country"));
        panelCenter.add(comboCountry);

        panelCenter.add(new JLabel("Credit Card Number"));
        panelCenter.add(textCreditCardNumber);

        panelCenter.add(new JLabel("Credit Card Type"));
        panelCenter.add(comboCreditCardType);
        
        panelCenter.add(new JLabel("Credit Card Expiry Date (MM/YY)"));
        panelCenter.add(textCreditCardExpiryDate);

        getContentPane().add(panelCenter, BorderLayout.CENTER);

        // Panel South
        getContentPane().add(manageBar, BorderLayout.SOUTH);
    }

    public void create(final CreateEvent evt) {
        final String mname = "create";

        // Sets all the Customer data
        final CustomerDTO customerDTO = new CustomerDTO(textId.getText(), textFirstname.getText(), textLastname.getText());
        customerDTO.setTelephone(textTelephone.getText());
        customerDTO.setEmail(textEmail.getText());
        customerDTO.setStreet1(textStreet1.getText());
        customerDTO.setStreet2(textStreet2.getText());
        customerDTO.setCity(textCity.getText());
        customerDTO.setState((String) comboState.getSelectedItem());
        customerDTO.setZipcode(textZipcode.getText());
        customerDTO.setCountry((String) comboCountry.getSelectedItem());
        customerDTO.setCreditCardNumber(textCreditCardNumber.getText());
        customerDTO.setCreditCardType((String) comboCreditCardType.getSelectedItem());
        customerDTO.setCreditCardExpiryDate(textCreditCardExpiryDate.getText());

        try {
            // Creates the customer
            CustomerDelegate.createCustomer(customerDTO);

        } catch (DuplicateKeyException e) {
            JOptionPane.showMessageDialog(this, "This Id already exists", "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot create the customer", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void delete(final DeleteEvent evt) {
        final String mname = "delete";

        try {
            // Deletes the customer
            CustomerDelegate.deleteCustomer(textId.getText());
            reset(new ResetEvent(this));

        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot delete the customer", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void find(final FindEvent evt) {
        final String mname = "find";

        try {
            // Finds the customer
            final CustomerDTO customerDTO = CustomerDelegate.findCustomer(textId.getText());

            // Displays the data
            textId.setText(customerDTO.getId());
            textFirstname.setText(customerDTO.getFirstname());
            textLastname.setText(customerDTO.getLastname());
            textTelephone.setText(customerDTO.getTelephone());
            textEmail.setText(customerDTO.getEmail());
            textStreet1.setText(customerDTO.getStreet1());
            textStreet2.setText(customerDTO.getStreet2());
            textCity.setText(customerDTO.getCity());
            comboState.setSelectedItem(customerDTO.getState());
            textZipcode.setText(customerDTO.getZipcode());
            comboCountry.setSelectedItem(customerDTO.getCountry());
            textCreditCardNumber.setText(customerDTO.getCreditCardNumber());
            comboCreditCardType.setSelectedItem(customerDTO.getCreditCardType());
            textCreditCardExpiryDate.setText(customerDTO.getCreditCardExpiryDate());
        } catch (ObjectNotFoundException e) {
            JOptionPane.showMessageDialog(this, "This customer has not been found", "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot find the customer", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void update(final UpdateEvent evt) {
        final String mname = "update";

        // Sets all the Customer data
        final CustomerDTO customerDTO = new CustomerDTO(textId.getText(), textFirstname.getText(), textLastname.getText());
        customerDTO.setTelephone(textTelephone.getText());
        customerDTO.setEmail(textEmail.getText());
        customerDTO.setStreet1(textStreet1.getText());
        customerDTO.setStreet2(textStreet2.getText());
        customerDTO.setCity(textCity.getText());
        customerDTO.setState((String) comboState.getSelectedItem());
        customerDTO.setZipcode(textZipcode.getText());
        customerDTO.setCountry((String) comboCountry.getSelectedItem());
        customerDTO.setCreditCardNumber(textCreditCardNumber.getText());
        customerDTO.setCreditCardType((String) comboCreditCardType.getSelectedItem());
        customerDTO.setCreditCardExpiryDate(textCreditCardExpiryDate.getText());

        try {
            // Updates the customer
            CustomerDelegate.updateCustomer(customerDTO);

        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot update the customer", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void reset(final ResetEvent evt) {
        textId.setText("");
        textFirstname.setText("");
        textLastname.setText("");
        textTelephone.setText("");
        textEmail.setText("");
        textStreet1.setText("");
        textStreet2.setText("");
        textCity.setText("");
        comboState.setSelectedItem("");
        textZipcode.setText("");
        comboCountry.setSelectedItem("");
        textCreditCardNumber.setText("");
        comboCreditCardType.setSelectedItem("");
        textCreditCardExpiryDate.setText("");    }

    public void close(final CloseEvent evt) {
        dispose();
    }
}
