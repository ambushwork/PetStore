package com.yaps.petstore.server.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.domain.category.CategoryDAO;
import com.yaps.petstore.server.domain.customer.Customer;
import com.yaps.petstore.server.domain.customer.CustomerDAO;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.server.domain.item.ItemDAO;
import com.yaps.petstore.server.domain.order.Order;
import com.yaps.petstore.server.domain.order.OrderDAO;
import com.yaps.petstore.server.domain.orderline.OrderLine;
import com.yaps.petstore.server.domain.orderline.OrderLineDAO;
import com.yaps.petstore.server.domain.product.Product;
import com.yaps.petstore.server.domain.product.ProductDAO;
import com.yaps.petstore.common.exception.*;

import junit.framework.TestSuite;

import java.util.Date;

/**
 * This class tests the OrderLineDAO class
 */
public final class OrderLineDAOTest extends AbstractTestCase {
    private final OrderLineDAO _orderLineDAO = new OrderLineDAO();
    private final OrderDAO _orderDAO = new OrderDAO();
    private final CategoryDAO _categoryDAO = new CategoryDAO();
    private final ProductDAO _productDAO = new ProductDAO();
    private final ItemDAO _itemDAO = new ItemDAO();
    private final CustomerDAO _customerDAO = new CustomerDAO();
    private final int _defaultQuantity = 12345;
    private final double _defaultUnitCost = 1.2345;

    public OrderLineDAOTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(OrderLineDAOTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testDomainFindOrderLineWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = getUniqueId();
        try {
            findOrderLine(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            _orderLineDAO.findByPrimaryKey(new String());
            fail("Object with empty id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with a null identifier
        try {
        	_orderLineDAO.findByPrimaryKey(null);
            fail("Object with null id should not be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllOrderLines() throws Exception {
        final String id = getUniqueId();

        // First findAll
        final int firstSize = findAllOrderLines();

        // Create an object
        final String orderLineId = createOrderLine(id);

        // Ensures that the object exists
        try {
            findOrderLine(orderLineId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllOrderLines();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeOrderLine(orderLineId);

        try {
            findOrderLine(orderLineId);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateOrderLine() throws Exception {
        final String id = getUniqueId();
        OrderLine orderLine = null;

        // Creates an object
        final String orderLineId = createOrderLine(id);

        // Ensures that the object exists
        try {
            orderLine = findOrderLine(orderLineId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkOrderLine(orderLine, id);

        // Cleans the test environment
        removeOrderLine(orderLineId);

        try {
            findOrderLine(orderLineId);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateOrderLineWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final OrderLine orderLine = new OrderLine(new String(), 0, 0, null, null);
            orderLine.checkData();
            _orderLineDAO.insert(orderLine);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final OrderLine orderLine = new OrderLine(null, 0, 0, null, null);
            orderLine.checkData();
            _orderLineDAO.insert(orderLine);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateOrderLineWithInvalidValues() throws Exception {

        // Creates an object
        final String id = getUniqueId();
        final String orderLineId = createOrderLine(id);

        // Ensures that the object exists
        OrderLine orderLine = null;
        try {
            orderLine = findOrderLine(orderLineId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            orderLine.setQuantity(0);
            orderLine.setUnitCost(0);
            orderLine.checkData();
            fail();
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            orderLine = findOrderLine(orderLineId);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Updates the object with valid values before removing it
        // since remove will call checkData()!
        orderLine.setQuantity(1);
        orderLine.setUnitCost(1);        
        // Cleans the test environment
        removeOrderLine(orderLineId);

        try {
            findOrderLine(orderLineId);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private OrderLine findOrderLine(final String id) throws FinderException, CheckException {
        final OrderLine orderLine = (OrderLine)_orderLineDAO.findByPrimaryKey(id);
        return orderLine;
    }

    private int findAllOrderLines() throws FinderException {
        try {
            return _orderLineDAO.findAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first, then a product linked to this category and an item linked to the product
    // Creates a Customer and an order linked to the customer
    // Creates an orderLine linked to the order and the item
    private String createOrderLine(final String id) throws CreateException, CheckException {
        // Create Category
    	String categoryId = _categoryDAO.getUniqueId();
        final Category category = new Category("cat" + categoryId, "name" + categoryId, "description" + categoryId);
        _categoryDAO.insert(category);
        // Create Product
    	String productId = _productDAO.getUniqueId();
        final Product product = new Product("prod" + productId, "name" + productId, "description" + productId, category);
        _productDAO.insert(product);
        // Create Item
    	String itemId = _itemDAO.getUniqueId();
        final Item item = new Item("item" + itemId, "name" + itemId, 1, product);
        _itemDAO.insert(item);

        // Create Customer
        String customerId = _customerDAO.getUniqueId();
        final Customer customer = new Customer(customerId, "firstname" + id, "lastname" + id);
        customer.setPassword(id);
        _customerDAO.insert(customer);
        // Create Order
        final Order order = new Order("order" + id, new Date(), "firstname" + id, "lastname" + id, "street1" + id, "city" + id, "zip" + id, "country" + id, customer);
        order.setStreet2("street2" + id);
        order.setCreditCardExpiryDate("ccexp" + id);
        order.setCreditCardNumber("ccnum" + id);
        order.setCreditCardType("cctyp" + id);
        order.setState("state" + id);
        _orderDAO.insert(order);

        // Create OrderLine
        final OrderLine orderLine = new OrderLine("order" + id, _defaultQuantity, _defaultUnitCost, order, item);
        _orderLineDAO.insert(orderLine);
        return orderLine.getId();
    }

    private void checkOrderLine(final OrderLine orderLine, final String id) {
        assertEquals("quantity", _defaultQuantity, orderLine.getQuantity());
    }

    private void removeOrderLine(final String orderLineId) throws RemoveException, ObjectNotFoundException {
    	final OrderLine orderLine = (OrderLine) _orderLineDAO.findByPrimaryKey(orderLineId);
    	final String orderId = orderLine.getId();
    	final Order order = (Order)_orderDAO.findByPrimaryKey(orderId);
    	final String customerId = order.getCustomer().getId();
    	final String itemId = orderLine.getItem().getId();
        Item item = (Item)_itemDAO.findByPrimaryKey(itemId);
        final String productId = item.getProduct().getId();
        Product product = (Product)_productDAO.findByPrimaryKey(productId);;
        final String categoryId = product.getCategory().getId();
    	_orderLineDAO.remove(orderLineId);
    	_orderDAO.remove(orderId);
    	_customerDAO.remove(customerId);
    	_itemDAO.remove(itemId);
    	_productDAO.remove(productId);
    	_categoryDAO.remove(categoryId);
    }

    protected String getUniqueId() {
    	String id = _orderLineDAO.getUniqueId();
    	return id;
    }    
}
