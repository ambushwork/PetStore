package com.yaps.petstore.server.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.server.domain.customer.Customer;
import com.yaps.petstore.server.domain.customer.CustomerDAO;
import com.yaps.petstore.server.domain.order.Order;
import com.yaps.petstore.server.domain.order.OrderDAO;
import com.yaps.petstore.common.exception.*;

import junit.framework.TestSuite;

import java.util.Date;

/**
 * This class tests the OrderDAO class
 */
public final class OrderDAOTest extends AbstractTestCase {
    private final OrderDAO _orderDAO = new OrderDAO();
    private final CustomerDAO _customerDAO = new CustomerDAO();

    public OrderDAOTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(OrderDAOTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testDomainFindOrderWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = getUniqueId();
        try {
            findOrder(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            _orderDAO.findByPrimaryKey(new String());
            fail("Object with empty id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with a null identifier
        try {
        	_orderDAO.findByPrimaryKey(null);
            fail("Object with null id should not be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllOrders() throws Exception {
        final String id = getUniqueId();

        // First findAll
        final int firstSize = findAllOrders();

        // Create an object
        final String orderId = createOrder(id);

        // Ensures that the object exists
        try {
            findOrder(orderId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllOrders();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeOrder(id, orderId);

        try {
            findOrder(orderId);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateOrder() throws Exception {
        final String id = getUniqueId();
        Order order = null;

        // Creates an object
        final String orderId = createOrder(id);

        // Ensures that the object exists
        try {
            order = findOrder(orderId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkOrder(order, id);

        // Cleans the test environment
        removeOrder(id, orderId);

        try {
            findOrder(orderId);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateOrderWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Order order = new Order(new String(), new String(), new String(), new String(), new String(), new String(), null);
            order.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final Order order = new Order(null, null, null, null, null, null, null);
            order.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateOrderWithInvalidValues() throws Exception {

        // Creates an object
        final String id = getUniqueId();
        final String orderId = createOrder(id);

        // Ensures that the object exists
        Order order = null;
        try {
            order = findOrder(orderId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            order.setStreet1(new String());
            order.setCountry(new String());
            order.setCity(new String());
            order.setZipcode(new String());
            order.checkData();
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            order.setStreet1(new String());
            order.setCountry(new String());
            order.setCity(new String());
            order.setZipcode(new String());
            order.checkData();
            fail("Updating an object with null values should break");
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            order = findOrder(orderId);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeOrder(id, orderId);

        try {
            findOrder(orderId);
            fail();
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateOrder() throws Exception {
        final String id = getUniqueId();

        // Creates an object
        final String orderId = createOrder(id);

        // Ensures that the object exists
        Order order = null;
        try {
            order = findOrder(orderId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkOrder(order, id);

        // Updates the object with new values
        updateOrder(order, id + 1);

        // Ensures that the object still exists
        Order orderUpdated = null;
        try {
            orderUpdated = findOrder(orderId);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkOrder(orderUpdated, id + 1);

        // Cleans the test environment
        removeOrder(id, orderId);

        try {
            findOrder(orderId);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Order findOrder(final String orderId) throws FinderException, CheckException {
        final Order order = (Order) _orderDAO.findByPrimaryKey(orderId);
        return order;
    }

    private int findAllOrders() throws FinderException {
        try {
            return _orderDAO.findAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a customer first and then a order linked to this customer and returns the key
    private String createOrder(final String id) throws CreateException, CheckException {
        // Create Customer
    	String customerId = _customerDAO.getUniqueId();
        final Customer customer = new Customer("custo" + customerId, "firstname" + customerId, "lastname" + customerId);
        customer.setPassword(id);
        _customerDAO.insert(customer);
        // Create Order
        final Order order = new Order(id, new Date(), "firstname" + id, "lastname" + id, "street1" + id, "city" + id, "zip" + id, "country" + id, customer);
        order.setStreet2("street2" + id);
        order.setCreditCardExpiryDate("ccexp" + id);
        order.setCreditCardNumber("ccnum" + id);
        order.setCreditCardType("cctyp" + id);
        order.setState("state" + id);
        _orderDAO.insert(order);
        return order.getId();
    }

    // Creates a customer and updates the order with this new customer
    private void updateOrder(final Order order, final String id) throws UpdateException, CreateException, ObjectNotFoundException {
    	String oldCustomerId = order.getCustomer().getId();
    	// Create new Customer
    	String customerId = _customerDAO.getUniqueId();
        final Customer customer = new Customer("custo" + customerId, "firstname" + customerId, "lastname" + customerId);
        customer.setPassword(id);
        _customerDAO.insert(customer);
        // Update Order with new customer
        order.setCustomer(customer);
        order.setFirstname("firstname" + id);
        order.setLastname("lastname" + id);
        order.setCountry("country" + id);
        order.setZipcode("zip" + id);
        order.setCity("city" + id);
        order.setStreet1("street1" + id);
        order.setStreet2("street2" + id);
        order.setCreditCardExpiryDate("ccexp" + id);
        order.setCreditCardNumber("ccnum" + id);
        order.setCreditCardType("cctyp" + id);
        order.setState("state" + id);
        _orderDAO.update(order);
        // remove old test Customer
    	_customerDAO.remove(oldCustomerId);
    }

    private void removeOrder(final String id, final String orderId) throws RemoveException, ObjectNotFoundException {
    	Order order = (Order)_orderDAO.findByPrimaryKey(id);
    	Customer customer = order.getCustomer();
    	_orderDAO.remove(orderId);
        _customerDAO.remove(customer.getId());
    }

    private void checkOrder(final Order order, final String id) {
        assertEquals("firstname", "firstname" + id, order.getFirstname());
        assertEquals("lastname", "lastname" + id, order.getLastname());
        assertEquals("street1", "street1" + id, order.getStreet1());
        assertEquals("street2", "street2" + id, order.getStreet2());
        assertEquals("city", "city" + id, order.getCity());
        assertEquals("zipcode", "zip" + id, order.getZipcode());
        assertEquals("country", "country" + id, order.getCountry());
        assertEquals("CreditCardExpiryDate", "ccexp" + id, order.getCreditCardExpiryDate());
        assertEquals("CreditCardNumber", "ccnum" + id, order.getCreditCardNumber());
        assertEquals("CreditCardType", "cctyp" + id, order.getCreditCardType());
        assertEquals("State", "state" + id, order.getState());
        assertNotNull("Customer", order.getCustomer());
    }

    protected String getUniqueId() {
    	String id = _orderDAO.getUniqueId();
    	return id;
    }    

}
