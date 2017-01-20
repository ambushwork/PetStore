package com.yaps.petstore.client.ui.list;

import com.yaps.petstore.common.delegate.CatalogDelegate;
import com.yaps.petstore.common.dto.ProductDTO;
import com.yaps.petstore.common.exception.FinderException;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class lists all the products of the system.
 */
public final class ListProductFrame extends AbstractListFrame {

    public ListProductFrame() {
        super();
        setTitle("Lists all the products");
    }

    protected String[] getColumnNames() {

        final String[] columnNames = {"ID", "Name", "Description", "Category Id", "Category Name"};
        return columnNames;
    }

    protected String[][] getData() throws FinderException, RemoteException {
        final String[][] data;

        final Collection productsDTO;

        productsDTO = CatalogDelegate.findProducts();
        data = new String[productsDTO.size()][5];

        int i = 0;
        for (Iterator iterator = productsDTO.iterator(); iterator.hasNext();) {
            final ProductDTO productDTO = (ProductDTO) iterator.next();
            data[i][0] = productDTO.getId();
            data[i][1] = productDTO.getName();
            data[i][2] = productDTO.getDescription();
            data[i][3] = productDTO.getCategoryId();
            data[i][4] = productDTO.getCategoryName();
            i++;
        }
        return data;
    }
}
