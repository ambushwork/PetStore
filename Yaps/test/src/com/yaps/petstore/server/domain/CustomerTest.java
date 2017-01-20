package com.yaps.petstore.server.domain;

import com.yaps.petstore.server.domain.customer.Customer;
import com.yaps.petstore.common.exception.CheckException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class tests the Customer class
 */
public final class CustomerTest extends TestCase {

    public CustomerTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CustomerTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================

    /**
     * This test tries to create an object with valid values.
     */
    public void testCreateValidCustomer() {

        // Creates a valid customer
        try {
        	final Customer customer = new Customer("bill000", "Bill", "Gates");
        	assertEquals("Bill", customer.getFirstname());
        	assertEquals("Gates", customer.getLastname());
        	customer.checkData();
        } catch (CheckException e) {
            fail("Custumer data is OK!");
        }
    }

    /**
     * This test tries to create an object with invalid values.
     */
    public void testCreateCustomerWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Customer customer = new Customer(new String(), new String(), new String());
        	customer.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid customer first name", e.getMessage());
        }

        // Creates an object with an null values
        try {
            final Customer customer = new Customer(null, null, null);
        	customer.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        	assertEquals("Invalid customer first name", e.getMessage());
        }
    }

}
