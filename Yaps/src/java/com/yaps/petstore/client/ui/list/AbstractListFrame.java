package com.yaps.petstore.client.ui.list;

import com.yaps.petstore.client.ui.AbstractFrame;
import com.yaps.petstore.common.exception.FinderException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.logging.Trace;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

/**
 * This abstract class helps concrete classes to display lists of data in a JTable.
 */
public abstract class AbstractListFrame extends AbstractFrame {

    private JTable table;

    public AbstractListFrame() {
        super();
        initComponents();
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // This method is called from within the constructor to display all the graphical components
    private void initComponents() {
        final String mname = "initComponents";

        try {
            table = new JTable(getData(), getColumnNames());
            table.setPreferredScrollableViewportSize(new Dimension(500, 70));

            final JScrollPane scrollPane = new JScrollPane(table);
            getContentPane().add(scrollPane);

        } catch (ObjectNotFoundException e) {
            // Nothing is done, list is empry
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot display the list: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    protected abstract String[] getColumnNames();

    // This method returns all the data to fill the table
    protected abstract String[][] getData() throws FinderException, RemoteException;
}
