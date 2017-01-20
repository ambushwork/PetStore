package com.yaps.petstore.server.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.server.domain.customer.Customer;
import com.yaps.petstore.server.domain.customer.CustomerDAO;
import com.yaps.petstore.common.exception.*;

import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class tests the CustomerDAO class
 */
public final class CustomerDAOTest extends AbstractTestCase {

    private final CustomerDAO _dao = new CustomerDAO();

    public CustomerDAOTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CustomerDAOTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testDomainFindCustomerWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = getUniqueId();
        try {
            findCustomer(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with a null identifier
        try {
            _dao.findByPrimaryKey(null);
            fail("Object with null id should not be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllCustomers() throws Exception {
        final String id = getUniqueId();

        // First findAll
        final int firstSize = findAllCustomers();

        // Create an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllCustomers();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateCustomer() throws Exception {
        final String id = getUniqueId();
        Customer customer = null;

        // Ensures that the object doesn't exist
        try {
            customer = findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

       // Checks that it's the right object
        checkCustomer(customer, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCustomer(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateCustomer() throws Exception {
        final String id = getUniqueId();

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        Customer customer = null;
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Updates the object with new values
        updateCustomer(customer, id + 1);

        // Ensures that the object still exists
        Customer customerUpdated = null;
        try {
            customerUpdated = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCustomer(customerUpdated, id + 1);

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

    }

    /**
     * This test ensures that the system cannot remove an unknown object
     */
    public void testDomainDeleteUnknownCustomer() throws Exception {
        // Removes an unknown object
        try {
            removeCustomer(getUniqueId());
            fail("Deleting an unknown object should break");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that the method for matching a password works.
     */
    public void testDomainMatchPasswordCustomer() throws Exception {
        final String id = getUniqueId();
        Customer customer = null;

        // Ensures that the object doesn't exist
        try {
            customer = findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Match invalid password
        try {
            customer.matchPassword("invalid password");
            fail("Invalid password shouldn't match");
        } catch (CheckException e) {
        }

        // Match valid password
        try {
            customer.matchPassword("pass" + id);
        } catch (CheckException e) {
            fail("Valid password should match");
        }

        // Cleans the test environment
        removeCustomer(id);
 }


    //==================================
    //=         Private Methods        =
    //==================================
    private Customer findCustomer(final String id) throws FinderException, CheckException {
        final Customer customer = (Customer)_dao.findByPrimaryKey("custo" + id);
        return customer;
    }

    private int findAllCustomers() throws FinderException {
        try {
            return _dao.selectAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private void createCustomer(final String id) throws CreateException, CheckException {
        final Customer customer = new Customer("custo" + id, "firstname" + id, "lastname" + id);
        customer.setPassword("pass" + id);
        customer.setCity("city" + id);
        customer.setCountry("cnty" + id);
        customer.setState("state" + id);
        customer.setStreet1("street1" + id);
        customer.setStreet2("street2" + id);
        customer.setTelephone("phone" + id);
        customer.setEmail("email" + id);
        customer.setZipcode("zip" + id);
        customer.setCreditCardExpiryDate("ccexp" + id);
        customer.setCreditCardNumber("ccnum" + id);
        customer.setCreditCardType("cctyp" + id);
         _dao.insert(customer);
    }

    private void updateCustomer(final Customer customer, final String id) 
    throws ObjectNotFoundException, DuplicateKeyException, CheckException {
        customer.setFirstname("firstname" + id);
        customer.setLastname("lastname" + id);
        customer.setPassword("pass" + id);
        customer.setCity("city" + id);
        customer.setCountry("cnty" + id);
        customer.setState("state" + id);
        customer.setStreet1("street1" + id);
        customer.setStreet2("street2" + id);
        customer.setTelephone("phone" + id);
        customer.setEmail("email" + id);
        customer.setZipcode("zip" + id);
        customer.setCreditCardExpiryDate("ccexp" + id);
        customer.setCreditCardNumber("ccnum" + id);
        customer.setCreditCardType("cctyp" + id);
  		_dao.update(customer);
    }

    private void removeCustomer(final String id) throws ObjectNotFoundException {
        final String sid = "custo" + id;
        _dao.remove(sid);
    }

    private void checkCustomer(final Customer customer, final String id) {
        assertEquals("firstname", "firstname" + id, customer.getFirstname());
        assertEquals("lastname", "lastname" + id, customer.getLastname());
        assertEquals("password", "pass" + id, customer.getPassword());
        assertEquals("city", "city" + id, customer.getCity());
        assertEquals("country", "cnty" + id, customer.getCountry());
        assertEquals("state", "state" + id, customer.getState());
        assertEquals("street1", "street1" + id, customer.getStreet1());
        assertEquals("street2", "street2" + id, customer.getStreet2());
        assertEquals("telephone", "phone" + id, customer.getTelephone());
        assertEquals("email", "email" + id, customer.getEmail());
        assertEquals("zipcode", "zip" + id, customer.getZipcode());
        assertEquals("CreditCardExpiryDate", "ccexp" + id, customer.getCreditCardExpiryDate());
        assertEquals("CreditCardNumber", "ccnum" + id, customer.getCreditCardNumber());
        assertEquals("CreditCardType", "cctyp" + id, customer.getCreditCardType());
     }

    protected String getUniqueId() {
    	String id = _dao.getUniqueId();
    	return id;
    }    
}
