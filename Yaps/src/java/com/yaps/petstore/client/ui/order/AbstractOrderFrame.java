package com.yaps.petstore.client.ui.order;

import com.yaps.petstore.client.ui.AbstractFrame;
import com.yaps.petstore.common.constant.AmericanStates;
import com.yaps.petstore.common.constant.Countries;
import com.yaps.petstore.common.constant.CreditCardTypes;

import javax.swing.*;
import java.awt.*;

/**
 * This abstract class helps concrete class to display Order information.
 */
public class AbstractOrderFrame extends AbstractFrame {

    final JLabel labelTitle = new JLabel();
    private final JPanel panelCenter = new JPanel();
    private final JPanel panelCenterNorth = new JPanel();
    private final JPanel panelCenterSouth = new JPanel();
    final JPanel panelSouth = new JPanel();

    final JTextField textOrderId = new JTextField();
    final JTextField textOrderDate = new JTextField();
    final JTextField textCustomerId = new JTextField();
    final JTextField textFirstName = new JTextField();
    final JTextField textLastName = new JTextField();
    final JTextField textStreet1 = new JTextField();
    final JTextField textStreet2 = new JTextField();
    final JTextField textCity = new JTextField();
    final JComboBox comboState = new JComboBox(AmericanStates.getAll());
    final JTextField textZipcode = new JTextField();
    final JComboBox comboCountry = new JComboBox(Countries.getAll());
    final JTextField textCCNumber = new JTextField();
    final JComboBox comboCCType = new JComboBox(CreditCardTypes.getAll());
    final JTextField textCCExpDate = new JTextField();

    final JTextField[] textItemId = new JTextField[5];
    final JTextField[] textItemName = new JTextField[5];
    final JTextField[] textItemQuantity = new JTextField[5];
    final JTextField[] textItemUnitCost = new JTextField[5];
    final JTextField[] textItemSubTotoal = new JTextField[5];
    final JTextField textTotal = new JTextField();

    // This method is called from within the constructor to display all the graphical components
    final void initComponents(final boolean writable) {
        panelCenter.setLayout(new GridLayout(2, 1));

        // Panel North
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setFont(new Font("Dialog", 1, 18));

        getContentPane().add(labelTitle, BorderLayout.NORTH);

        // Panel Center North
        panelCenterNorth.setLayout(new GridLayout(14, 2));

        panelCenterNorth.add(new JLabel("Order ID"));
        textOrderId.setEnabled(!writable);
        panelCenterNorth.add(textOrderId);

        panelCenterNorth.add(new JLabel("Order Date"));
        textOrderDate.setEnabled(false);
        panelCenterNorth.add(textOrderDate);

        panelCenterNorth.add(new JLabel("Customer ID"));
        textCustomerId.setEnabled(writable);
        panelCenterNorth.add(textCustomerId);

        panelCenterNorth.add(new JLabel("First Name"));
        textFirstName.setEnabled(writable);
        panelCenterNorth.add(textFirstName);

        panelCenterNorth.add(new JLabel("Last Name"));
        textLastName.setEnabled(writable);
        panelCenterNorth.add(textLastName);

        panelCenterNorth.add(new JLabel("Street 1"));
        textStreet1.setEnabled(writable);
        panelCenterNorth.add(textStreet1);

        panelCenterNorth.add(new JLabel("Street 2"));
        textStreet2.setEnabled(writable);
        panelCenterNorth.add(textStreet2);

        panelCenterNorth.add(new JLabel("City"));
        textCity.setEnabled(writable);
        panelCenterNorth.add(textCity);

        panelCenterNorth.add(new JLabel("State"));
        comboState.setEnabled(writable);
        panelCenterNorth.add(comboState);

        panelCenterNorth.add(new JLabel("Zipcode"));
        textZipcode.setEnabled(writable);
        panelCenterNorth.add(textZipcode);

        panelCenterNorth.add(new JLabel("Country"));
        comboCountry.setEnabled(writable);
        panelCenterNorth.add(comboCountry);

        panelCenterNorth.add(new JLabel("Credit Card Number"));
        textCCNumber.setEnabled(writable);
        panelCenterNorth.add(textCCNumber);

        panelCenterNorth.add(new JLabel("Credit Card Type"));
        comboCCType.setEnabled(writable);
        panelCenterNorth.add(comboCCType);
        panelCenterNorth.add(new JLabel("Credit Card Expiry Date (MM/YY)"));
        textCCExpDate.setEnabled(writable);
        panelCenterNorth.add(textCCExpDate);

        panelCenter.add(panelCenterNorth);
        panelCenter.add(panelCenterSouth);
        getContentPane().add(panelCenter, BorderLayout.CENTER);

        // Panel Center South
        panelCenterSouth.setLayout(new GridLayout(7, 6));

        panelCenterSouth.add(new JLabel("Item Id"));
        panelCenterSouth.add(new JLabel("Item Name"));
        panelCenterSouth.add(new JLabel("Unit Cost"));
        panelCenterSouth.add(new JLabel("Quantity"));
        panelCenterSouth.add(new JLabel("Sub total"));

        for (int i = 0; i < 5; i++) {
            textItemId[i] = new JTextField();
            textItemId[i].setEnabled(writable);
            panelCenterSouth.add(textItemId[i]);

            textItemName[i] = new JTextField();
            textItemName[i].setEnabled(false);
            panelCenterSouth.add(textItemName[i]);

            textItemUnitCost[i] = new JTextField();
            textItemUnitCost[i].setEnabled(false);
            panelCenterSouth.add(textItemUnitCost[i]);

            textItemQuantity[i] = new JTextField();
            textItemQuantity[i].setEnabled(writable);
            panelCenterSouth.add(textItemQuantity[i]);

            textItemSubTotoal[i] = new JTextField();
            textItemSubTotoal[i].setEnabled(false);
            panelCenterSouth.add(textItemSubTotoal[i]);
        }

        panelCenterSouth.add(new JLabel("Total"));
        panelCenterSouth.add(new JLabel());
        panelCenterSouth.add(new JLabel());
        panelCenterSouth.add(new JLabel());
        textTotal.setEnabled(false);
        panelCenterSouth.add(textTotal);
    }

    final void clearFrame() {
        textOrderId.setText("");
        textOrderDate.setText("");
        textCustomerId.setText("");
        textFirstName.setText("");
        textLastName.setText("");
        textStreet1.setText("");
        textStreet2.setText("");
        textCity.setText("");
        comboState.setSelectedItem("");
        textZipcode.setText("");
        comboCountry.setSelectedItem("");
        textCCNumber.setText("");
        comboCCType.setSelectedItem("");        textCCExpDate.setText("");
        for (int i = 0; i < 5; i++) {
            textItemId[i].setText("");
            textItemName[i].setText("");
            textItemQuantity[i].setText("");
            textItemUnitCost[i].setText("");
            textItemSubTotoal[i].setText("");
        }
    }
}
