package com.yaps.petstore.client.ui.list;

import com.yaps.petstore.common.delegate.CatalogDelegate;
import com.yaps.petstore.common.dto.CategoryDTO;
import com.yaps.petstore.common.exception.FinderException;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class lists all the categories of the system.
 */
public final class ListCategoryFrame extends AbstractListFrame {

    public ListCategoryFrame() {
        super();
        setTitle("Lists all the categories");
    }

    protected String[] getColumnNames() {

        final String[] columnNames = {"ID", "Name", "Description"};

        return columnNames;
    }

    protected String[][] getData() throws FinderException, RemoteException {
        final String[][] data;

        final Collection categoriesDTO;

        categoriesDTO = CatalogDelegate.findCategories();
        data = new String[categoriesDTO.size()][3];

        int i = 0;
        for (Iterator iterator = categoriesDTO.iterator(); iterator.hasNext();) {
            final CategoryDTO categoryDTO = (CategoryDTO) iterator.next();
            data[i][0] = categoryDTO.getId();
            data[i][1] = categoryDTO.getName();
            data[i][2] = categoryDTO.getDescription();
            i++;
        }
        return data;
    }
}
