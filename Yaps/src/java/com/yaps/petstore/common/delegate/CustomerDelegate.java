package com.yaps.petstore.common.delegate;

import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.common.locator.ServiceLocator;
import com.yaps.petstore.server.service.customer.CustomerService;
import com.yaps.petstore.server.service.customer.CustomerServiceHome;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 * This class follows the Delegate design pattern. It's a one to one method
 * with the CustomerService class. Each method delegates the call to the
 * CustomerService class
 */
public final class CustomerDelegate {

    // ======================================
    // =             Attributes             =
    // ======================================

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * Delegates the call to the {@link CustomerService#authenticate(String, String) CustomerService().authenticate} method.
     */
    public static CustomerDTO authenticate(final String customerId, final String password) throws FinderException, CheckException, RemoteException {
        return getCustomerService().authenticate(customerId, password);
    }

    /**
     * Delegates the call to the {@link CustomerService#createCustomer(CustomerDTO) CustomerService().createCustomer} method.
     */
    public static CustomerDTO createCustomer(final CustomerDTO customerDTO) throws CreateException, CheckException, RemoteException {
        return getCustomerService().createCustomer(customerDTO);
    }

    /**
     * Delegates the call to the {@link CustomerService#findCustomer(String) CustomerService().findCustomer} method.
     */
    public static CustomerDTO findCustomer(final String customerId) throws FinderException, CheckException, RemoteException {
        return getCustomerService().findCustomer(customerId);
    }

    /**
     * Delegates the call to the {@link CustomerService#deleteCustomer(String) CustomerService().deleteCustomer} method.
     */
    public static void deleteCustomer(final String customerId) throws RemoveException, CheckException, RemoteException {
        getCustomerService().deleteCustomer(customerId);
    }

    /**
     * Delegates the call to the {@link CustomerService#updateCustomer(CustomerDTO) CustomerService().updateCustomer} method.
     */
    public static void updateCustomer(final CustomerDTO customerDTO) throws UpdateException, CheckException, RemoteException {
        getCustomerService().updateCustomer(customerDTO);
    }

    /**
     * Delegates the call to the {@link CustomerService#findCustomers() CustomerService().findCustomers} method.
     */
    public static Collection findCustomers() throws FinderException, RemoteException {
        return getCustomerService().findCustomers();
    }

    // ======================================
    // =            Private methods         =
    // ======================================
    private static CustomerService getCustomerService() throws RemoteException {
        CustomerService customerServiceRemote = null;
        try {
            // Looks up for the home interface
            customerServiceRemote = (CustomerService) ServiceLocator.getInstance().getHome(CustomerServiceHome.JNDI_NAME);
            // customerServiceRemote = (CustomerService) ServiceLocator.getInstance().getHome(CustomerServiceHome.JNDI_NAME_FOR_REMOTE_CLIENTS);
        } catch (Exception e) {
            throw new RemoteException("Lookup or Create exception", e);
        }

        return customerServiceRemote;
    }
}
