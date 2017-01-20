package com.yaps.petstore.client.ui.referential;

import com.yaps.petstore.client.ui.AbstractFrame;
import com.yaps.petstore.client.ui.referential.bar.*;
import com.yaps.petstore.common.delegate.CatalogDelegate;
import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.DuplicateKeyException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.logging.Trace;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the graphical user interface that enables an employee
 * to create, find, delete and update a Item.
 */
public final class ManageItemFrame extends AbstractFrame implements BarEventListener {

    // Variables declaration
    private final JLabel labelTitle = new JLabel();
    private final JPanel panelCenter = new JPanel();
    private final JTextField textId = new JTextField();
    private final JTextField textName = new JTextField();
    private final JTextField textUnitCost = new JTextField();
    private final JTextField textImagePath = new JTextField();
    private final JTextField textProductId = new JTextField();
    private final JTextField textProductName = new JTextField();

    private final ManageBar manageBar = new ManageBar();

    /**
     * Creates new form
     */
    public ManageItemFrame() {
        initComponents();
        pack();
        manageBar.setManageListener(this);
    }

    // This method is called from within the constructor to display all the graphical components
    private void initComponents() {
        // Panel North
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setFont(new Font("Dialog", 1, 18));
        labelTitle.setText("Item");

        getContentPane().add(labelTitle, BorderLayout.NORTH);

        // Panel Center
        panelCenter.setLayout(new GridLayout(6, 2));

        panelCenter.add(new JLabel("Identifier"));
        panelCenter.add(textId);

        panelCenter.add(new JLabel("Name"));
        panelCenter.add(textName);

        panelCenter.add(new JLabel("Unit Cost"));
        panelCenter.add(textUnitCost);

        panelCenter.add(new JLabel("Image Path"));
        panelCenter.add(textImagePath);

        panelCenter.add(new JLabel("Product Id"));
        panelCenter.add(textProductId);

        panelCenter.add(new JLabel("Product Name"));
        textProductName.setEnabled(false);
        panelCenter.add(textProductName);

        getContentPane().add(panelCenter, BorderLayout.CENTER);

        // Panel South
        getContentPane().add(manageBar, BorderLayout.SOUTH);
    }

    public void create(final CreateEvent evt) {
        final String mname = "create";

        // Sets all the item data
        final ItemDTO itemDTO = new ItemDTO(textId.getText(), textName.getText(), Double.parseDouble(textUnitCost.getText()));
        itemDTO.setImagePath(textImagePath.getText());
        itemDTO.setProductId(textProductId.getText());

        try {
            // Creates the item
            CatalogDelegate.createItem(itemDTO);

        } catch (DuplicateKeyException e) {
            JOptionPane.showMessageDialog(this, "This Id already exists", "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot create the item", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void delete(final DeleteEvent evt) {
        final String mname = "delete";

        try {
            // Deletes the item
            CatalogDelegate.deleteItem(textId.getText());
            reset(new ResetEvent(this));

        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot delete the item", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void find(final FindEvent evt) {
        final String mname = "find";

        try {
            // Finds the item
            final ItemDTO itemDTO = CatalogDelegate.findItem(textId.getText());

            // Displays the data
            textId.setText(itemDTO.getId());
            textName.setText(itemDTO.getName());
            textUnitCost.setText(new Double(itemDTO.getUnitCost()).toString());
            textImagePath.setText(itemDTO.getImagePath());
            textProductId.setText(itemDTO.getProductId());
            textProductName.setText(itemDTO.getProductName());

        } catch (ObjectNotFoundException e) {
            JOptionPane.showMessageDialog(this, "This item has not been found", "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot find the item", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void update(final UpdateEvent evt) {
        final String mname = "update";

        try {
            // Sets all the item data
            final ItemDTO itemDTO = new ItemDTO(textId.getText(), textName.getText(), Double.parseDouble(textUnitCost.getText()));
            itemDTO.setImagePath(textImagePath.getText());
            itemDTO.setProductId(textProductId.getText());

            // Creates the item
            CatalogDelegate.updateItem(itemDTO);

        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot update the item", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void reset(final ResetEvent evt) {
        textId.setText("");
        textName.setText("");
        textUnitCost.setText("");
        textImagePath.setText("");
        textProductId.setText("");
        textProductName.setText("");
    }

    public void close(final CloseEvent evt) {
        dispose();
    }
}
