package com.yaps.petstore.client.ui.list;

import com.yaps.petstore.common.delegate.CustomerDelegate;
import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.FinderException;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class lists all the customers of the system.
 */
public final class ListCustomerFrame extends AbstractListFrame {

    public ListCustomerFrame() {
        super();
        setTitle("Lists all the customers");
    }

    protected String[] getColumnNames() {

        final String[] columnNames = {"ID", "First Name", "Last Name", "Password", "Telephone", "Email", "Street1",
                                      "Street2", "City", "State", "Zipcode", "Country",
                                      "Credit Card Number", "Type", "Expiry Date"};
        return columnNames;
    }

    protected String[][] getData() throws FinderException, RemoteException {
        final String[][] data;

        final Collection customersDTO;

        customersDTO = CustomerDelegate.findCustomers();
        data = new String[customersDTO.size()][15];

        int i = 0;
        for (Iterator iterator = customersDTO.iterator(); iterator.hasNext();) {
            final CustomerDTO customerDTO = (CustomerDTO) iterator.next();
            data[i][0] = customerDTO.getId();
            data[i][1] = customerDTO.getFirstname();
            data[i][2] = customerDTO.getLastname();
            data[i][3] = customerDTO.getPassword();
            data[i][4] = customerDTO.getTelephone();
            data[i][5] = customerDTO.getEmail();
            data[i][6] = customerDTO.getStreet1();
            data[i][7] = customerDTO.getStreet2();
            data[i][8] = customerDTO.getCity();
            data[i][9] = customerDTO.getState();
            data[i][10] = customerDTO.getZipcode();
            data[i][11] = customerDTO.getCountry();
            data[i][12] = customerDTO.getCreditCardNumber();
            data[i][13] = customerDTO.getCreditCardType();
            data[i][14] = customerDTO.getCreditCardExpiryDate();
            i++;
        }
        return data;
    }
}
