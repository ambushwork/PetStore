package com.yaps.petstore.client.ui.list;

import com.yaps.petstore.common.delegate.CatalogDelegate;
import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.exception.FinderException;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class lists all the items of the system.
 */
public final class ListItemFrame extends AbstractListFrame {

    public ListItemFrame() {
        super();
        setTitle("Lists all the items");
    }

    protected String[] getColumnNames() {

        final String[] columnNames = {"ID", "Name", "Unit Cost", "Image Path", "Product Id", "Product Name"};
        return columnNames;
    }

    protected String[][] getData() throws FinderException, RemoteException {
        final String[][] data;

        final Collection itemsDTO;

        itemsDTO = CatalogDelegate.findItems();
        data = new String[itemsDTO.size()][6];

        int i = 0;
        for (Iterator iterator = itemsDTO.iterator(); iterator.hasNext();) {
            final ItemDTO itemDTO = (ItemDTO) iterator.next();
            data[i][0] = itemDTO.getId();
            data[i][1] = itemDTO.getName();
            data[i][2] = String.valueOf(itemDTO.getUnitCost());
            data[i][3] = itemDTO.getImagePath();
            data[i][4] = itemDTO.getProductId();
            data[i][5] = itemDTO.getProductName();
            i++;
        }
        return data;
    }
}
