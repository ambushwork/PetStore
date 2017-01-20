package com.yaps.petstore.client.ui;

import com.yaps.petstore.client.ui.list.ListCategoryFrame;
import com.yaps.petstore.client.ui.list.ListCustomerFrame;
import com.yaps.petstore.client.ui.list.ListItemFrame;
import com.yaps.petstore.client.ui.list.ListProductFrame;
import com.yaps.petstore.client.ui.order.CreateOrderFrame;
import com.yaps.petstore.client.ui.order.ManageOrderFrame;
import com.yaps.petstore.client.ui.referential.ManageCategoryFrame;
import com.yaps.petstore.client.ui.referential.ManageCustomerFrame;
import com.yaps.petstore.client.ui.referential.ManageItemFrame;
import com.yaps.petstore.client.ui.referential.ManageProductFrame;
import com.yaps.petstore.common.logging.Trace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the main user interface that displays a menu from which the
 * employee can do some actions on the system.
 */
public final class Menu extends AbstractFrame {

    // Variables declaration
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menuFile = new JMenu();
    private final JMenuItem menuItemExit = new JMenuItem();
    private final JMenu menuOrder = new JMenu();
    private final JMenuItem menuItemCreateOrder = new JMenuItem();
    private final JMenuItem menuItemManageOrder = new JMenuItem();
    private final JMenu menuReferential = new JMenu();
    private final JMenuItem menuItemManageCategory = new JMenuItem();
    private final JMenuItem menuItemManageCustomer = new JMenuItem();
    private final JMenuItem menuItemManageProduct = new JMenuItem();
    private final JMenuItem menuItemManageItem = new JMenuItem();
    private final JMenu menuList = new JMenu();
    private final JMenuItem menuListCustomer = new JMenuItem();
    private final JMenuItem menuListCategory = new JMenuItem();
    private final JMenuItem menuListProduct = new JMenuItem();
    private final JMenuItem menuListItem = new JMenuItem();
    private final JMenu menuLookAndFeel = new JMenu();
    private final JMenuItem menuItemMetal = new JMenuItem();
    private final JMenuItem menuItemMotif = new JMenuItem();
    private final JMenuItem menuItemWindows = new JMenuItem();

    /**
     * Creates new form Menu.
     */
    public Menu() {
        initComponents();
        setSize(400, 300);
        setTitle("Pet Store");
        setJMenuBar(menuBar);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // This method is called from within the constructor to display all the graphical components
    private void initComponents() {

        // Menu File
        menuFile.setText("File");

        menuItemExit.setText("Exit");
        menuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuItemExitActionPerformed();
            }
        });

        menuFile.add(menuItemExit);
        menuBar.add(menuFile);

        // Menu Order
        menuOrder.setText("Order");

        menuItemCreateOrder.setText("Create Order");
        menuItemCreateOrder.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuItemCreateOrderActionPerformed();
            }
        });
        menuOrder.add(menuItemCreateOrder);

        menuItemManageOrder.setText("Manage Order");
        menuItemManageOrder.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuItemManageOrderActionPerformed();
            }
        });
        menuOrder.add(menuItemManageOrder);

        menuBar.add(menuOrder);

        // Menu Referential
        menuReferential.setText("Referential");

        menuItemManageCustomer.setText("Manage Customer");
        menuItemManageCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuItemManageCustomerActionPerformed();
            }
        });
        menuReferential.add(menuItemManageCustomer);

        menuItemManageCategory.setText("Manage Category");
        menuItemManageCategory.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuItemManageCategoryActionPerformed();
            }
        });
        menuReferential.add(menuItemManageCategory);

        menuItemManageProduct.setText("Manage Product");
        menuItemManageProduct.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuItemManageProductActionPerformed();
            }
        });
        menuReferential.add(menuItemManageProduct);

        menuItemManageItem.setText("Manage Item");
        menuItemManageItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuItemManageItemActionPerformed();
            }
        });
        menuReferential.add(menuItemManageItem);


        menuBar.add(menuReferential);

        // Menu List
        menuList.setText("Lists");

        menuListCustomer.setText("List customers");
        menuListCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuListCustomerActionPerformed();
            }
        });
        menuList.add(menuListCustomer);

        menuListCategory.setText("List categories");
        menuListCategory.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuListCategoryActionPerformed();
            }
        });
        menuList.add(menuListCategory);

        menuListProduct.setText("List products");
        menuListProduct.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuListProductActionPerformed();
            }
        });
        menuList.add(menuListProduct);

        menuListItem.setText("List items");
        menuListItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuListItemActionPerformed();
            }
        });
        menuList.add(menuListItem);

        menuBar.add(menuList);

        // Menu Look & Feel
        menuLookAndFeel.setText("Look&Feel");

        menuItemMetal.setText("Metal");
        menuItemMetal.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuItemMetalActionPerformed();
            }
        });

        menuLookAndFeel.add(menuItemMetal);
        menuItemMotif.setText("Motif");
        menuItemMotif.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuItemMotifActionPerformed();
            }
        });

        menuLookAndFeel.add(menuItemMotif);
        menuItemWindows.setText("Windows");
        menuItemWindows.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                menuItemWindowsActionPerformed();
            }
        });

        menuLookAndFeel.add(menuItemWindows);
        menuBar.add(menuLookAndFeel);
        setName("frameMenu");
    }

    // Clicking on the 'Motif' menu changes the look and feel of the application
    private void menuItemMotifActionPerformed() {
        final String mname = "menuItemMotifActionPerformed";

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
        }
    }

    // Clicking on the 'Metal' menu changes the look and feel of the application
    private void menuItemMetalActionPerformed() {
        final String mname = "menuItemMetalActionPerformed";

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
        }
    }

    // Clicking on the 'Windows' menu changes the look and feel of the application
    private void menuItemWindowsActionPerformed() {
        final String mname = "menuItemWindowsActionPerformed";

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
        }
    }

    // This method opens the Create Order frame
    private void menuItemCreateOrderActionPerformed() {
        new CreateOrderFrame().show();
    }

    // This method opens the Manage Order frame
    private void menuItemManageOrderActionPerformed() {
        new ManageOrderFrame().show();
    }


    // This method opens the Manage Customer frame
    private void menuItemManageCustomerActionPerformed() {
        new ManageCustomerFrame().show();
    }

    // This method opens the Manage Category frame
    private void menuItemManageCategoryActionPerformed() {
        new ManageCategoryFrame().show();
    }

    // This method opens the Manage Product frame
    private void menuItemManageProductActionPerformed() {
        new ManageProductFrame().show();
    }

    // This method opens the Manage Item frame
    private void menuItemManageItemActionPerformed() {
        new ManageItemFrame().show();
    }

    // This method opens the Customer List frame
    private void menuListCustomerActionPerformed() {
        new ListCustomerFrame().show();
    }

    // This method opens the Category List frame
    private void menuListCategoryActionPerformed() {
        new ListCategoryFrame().show();
    }

    // This method opens the Product List frame
    private void menuListProductActionPerformed() {
        new ListProductFrame().show();
    }

    // This method opens the Item List frame
    private void menuListItemActionPerformed() {
        new ListItemFrame().show();
    }

    // This method exits the application
    private void menuItemExitActionPerformed() {
        dispose();
    }

    /**
     * @param args the command line arguments.
     */
    public static void main(final String[] args) {
        new Menu().show();
    }

}
