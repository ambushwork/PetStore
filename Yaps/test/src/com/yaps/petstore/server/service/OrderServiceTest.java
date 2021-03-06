package com.yaps.petstore.server.service;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.dto.*;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.common.locator.ServiceLocator;
import com.yaps.petstore.server.service.catalog.CatalogService;
import com.yaps.petstore.server.service.catalog.CatalogServiceHome;
import com.yaps.petstore.server.service.customer.CustomerService;
import com.yaps.petstore.server.service.customer.CustomerServiceHome;
import com.yaps.petstore.server.service.order.OrderService;
import com.yaps.petstore.server.service.order.OrderServiceHome;
import junit.framework.TestSuite;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class tests the CatalogService class
 */
public final class OrderServiceTest extends AbstractTestCase {

    public OrderServiceTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(OrderServiceTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testServiceFindOrderWithInvalidValues() throws Exception {
        final OrderService service = getOrderService();

        // Finds an object with a unknown identifier
        final String id = getUniqueId("Order");
        try {
            service.findOrder(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            service.findOrder(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }


        // Finds an object with a null identifier
        try {
            service.findOrder(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testServiceCreateOrder() throws Exception {
        final String id = getUniqueId("Order");
        OrderDTO orderDTO = null;

        // Creates an object
        final String orderId = createOrder(id);

        // Ensures that the object exists
        try {
            orderDTO = findOrder(orderId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkOrder(orderDTO, id);

        // Cleans the test environment
        deleteOrder(orderId);

        try {
            findOrder(orderId);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServiceCreateOrderWithInvalidValues() throws Exception {
        final OrderService service = getOrderService();
        OrderDTO orderDTO;

        // Creates an object with a null parameter
        try {
            service.createOrder(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
            orderDTO = new OrderDTO(new String(), new String(), new String(), new String(), new String(), new String());
            service.createOrder(orderDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            orderDTO = new OrderDTO(null, null, null, null, null, null);
            service.createOrder(orderDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This method ensures that creating an object with invalid credit card information
     * doesn't work.
     */
    public void testServiceCreateOrderWithInvalidCreditCard() throws Exception {
        final String id = getUniqueId("Order");
        final OrderService orderService = getOrderService();

        // Create Category 
        final String categoryId = getUniqueId("Category");
        final CategoryDTO categoryDTO = new CategoryDTO("cat" + categoryId, "name" + categoryId, "description" + categoryId);
        getCatalogService().createCategory(categoryDTO);
        // Create Product
        final String productId = getUniqueId("Product");
        final ProductDTO productDTO = new ProductDTO("prod" + productId, "name" + productId, "description" + productId);
        productDTO.setCategoryId("cat" + categoryId);
        getCatalogService().createProduct(productDTO);
        // Create Item
        final String itemId = getUniqueId("Item");
        final ItemDTO itemDTO = new ItemDTO("item" + itemId, "name" + itemId, Double.parseDouble(itemId));
        itemDTO.setProductId("prod" + productId);
        getCatalogService().createItem(itemDTO);
        // Create Customer
        final CustomerDTO customerDTO = new CustomerDTO("custo" + id, "firstname" + id, "lastname" + id);
        customerDTO.setPassword("password");
        getCustomerService().createCustomer(customerDTO);

        // Creates two order lines
        final OrderLineDTO oi1 = new OrderLineDTO(Integer.parseInt(id), itemDTO.getUnitCost());
        oi1.setItemId(itemDTO.getId());
        final OrderLineDTO oi2 = new OrderLineDTO(Integer.parseInt(id), itemDTO.getUnitCost());
        oi2.setItemId(itemDTO.getId());
        final Collection orderLines = new ArrayList();
        orderLines.add(oi1);
        orderLines.add(oi2);

        // Create Order..
        OrderDTO orderDTO = new OrderDTO("firstname" + id, "lastname" + id, "street1" + id, "city" + id, "zip" + id, "country" + id);
        orderDTO.setCustomerId(customerDTO.getId());
        orderDTO.setOrderLines(orderLines);

        // ... with invalid credit card date
        orderDTO.setCreditCardExpiryDate("10/02");
        orderDTO.setCreditCardNumber("4564 1231 4564 2222");
        orderDTO.setCreditCardType("Visa");
        try {
            orderService.createOrder(orderDTO);
            fail("Credit card date was invalid. Object shouldn't be created");
        } catch (CheckException e) {
        }

        // ... with invalid credit card number for a visa
        orderDTO.setCreditCardExpiryDate("10/18");
        orderDTO.setCreditCardNumber("4564 1231 4564 1111");
        orderDTO.setCreditCardType("Visa");
        try {
            orderService.createOrder(orderDTO);
            fail("Credit card number was invalid. Object shouldn't be created");
        } catch (CheckException e) {
        }

        // The client doesn't pay with the credit card but with a cheque
        orderDTO.setCreditCardExpiryDate("");
        orderDTO.setCreditCardNumber("");
        orderDTO.setCreditCardType("");
        try {
            orderDTO = orderService.createOrder(orderDTO);
        } catch (CreateException e) {
            fail("Credit card wasn't used. Object should be created");
        }

        // Ensures that the object exists
        try {
            orderDTO = findOrder(orderDTO.getId());
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Cleans the test environment
        deleteOrder(orderDTO.getId());

        try {
            findOrder(orderDTO.getId());
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteInvalidOrder() throws Exception {

        // Deletes an object with null id
        try {
            deleteOrder(null);
            fail("Object with null id should not be deleted");
        } catch (CheckException e) {
        }

        // Deletes an object with null id
        try {
            deleteOrder(new String());
            fail("Object with empty id should not be deleted");
        } catch (CheckException e) {
        }
    }

    //==================================
    //=          Private Methods       =
    //==================================
    private OrderService getOrderService() throws RemoteException {
        OrderService orderServiceRemote = null;
        try {
            orderServiceRemote = (OrderService) ServiceLocator.getInstance().getHome(OrderServiceHome.JNDI_NAME);
        } catch (Exception e) {
            throw new RemoteException("Lookup exception", e);
        }

        return orderServiceRemote;
    }

    private CustomerService getCustomerService() throws RemoteException{
        CustomerService customerServiceRemote = null;
        try {
            customerServiceRemote = (CustomerService) ServiceLocator.getInstance().getHome(CustomerServiceHome.JNDI_NAME);
        } catch (Exception e) {
            throw new RemoteException("Lookup exception", e);
        }

        return customerServiceRemote;
    }

    private CatalogService getCatalogService() throws RemoteException{
        CatalogService catalogServiceRemote = null;
        try {
            catalogServiceRemote = (CatalogService) ServiceLocator.getInstance().getHome(CatalogServiceHome.JNDI_NAME);
        } catch (Exception e) {
            throw new RemoteException("Lookup exception", e);
        }
        return catalogServiceRemote;
    }

    private OrderDTO findOrder(final String id) throws FinderException, CheckException, RemoteException {
        final OrderDTO orderDTO = getOrderService().findOrder(id);
        return orderDTO;
    }

    // Creates a category first, then a product linked to this category and an item linked to the product
    // Creates a Customer and an order linked to the customer
    // Creates an orderLine linked to the order and the item
    private String createOrder(final String id) throws CreateException, CheckException, RemoteException {
        // Create Category 
    	final String categoryId = getUniqueId("Category");
        final CategoryDTO categoryDTO = new CategoryDTO("cat" + categoryId, "name" + categoryId, "description" + categoryId);
        getCatalogService().createCategory(categoryDTO);
        // Create Product
        final String productId = getUniqueId("Product");
        final ProductDTO productDTO = new ProductDTO("prod" + productId, "name" + productId, "description" + productId);
        productDTO.setCategoryId("cat" + categoryId);
        getCatalogService().createProduct(productDTO);
        // Create Item
        final String itemId = getUniqueId("Item");
        final ItemDTO itemDTO = new ItemDTO("item" + itemId, "name" + itemId, Double.parseDouble(itemId));
        itemDTO.setProductId("prod" + productId);
        getCatalogService().createItem(itemDTO);
        // Create Customer
        final CustomerDTO customerDTO = new CustomerDTO("custo" + id, "firstname" + id, "lastname" + id);
        customerDTO.setPassword("password");
        getCustomerService().createCustomer(customerDTO);

        // Create Order
        final OrderDTO orderDTO = new OrderDTO("firstname" + id, "lastname" + id, "street1" + id, "city" + id, "zip" + id, "country" + id);
        orderDTO.setStreet2("street2" + id);
        orderDTO.setCreditCardExpiryDate("10/18");
        orderDTO.setCreditCardNumber("4564 1231 4564 1222");
        orderDTO.setCreditCardType("Visa");
        orderDTO.setState("state" + id);
        orderDTO.setCustomerId(customerDTO.getId());

        // Creates two order lines
        final OrderLineDTO oi1 = new OrderLineDTO(Integer.parseInt(id), itemDTO.getUnitCost());
        oi1.setItemId(itemDTO.getId());
        final OrderLineDTO oi2 = new OrderLineDTO(Integer.parseInt(id), itemDTO.getUnitCost());
        oi2.setItemId(itemDTO.getId());
        final Collection orderLines = new ArrayList();
        orderLines.add(oi1);
        orderLines.add(oi2);
        orderDTO.setOrderLines(orderLines);

        final OrderDTO result = getOrderService().createOrder(orderDTO);
        return result.getId();
    }

    private void deleteOrder(final String orderId) throws RemoveException, CheckException, RemoteException {
        getOrderService().deleteOrder(orderId);
    }

    private void checkOrder(final OrderDTO orderDTO, final String id) {
        assertEquals("firstname", "firstname" + id, orderDTO.getFirstname());
        assertEquals("lastname", "lastname" + id, orderDTO.getLastname());
        assertEquals("city", "city" + id, orderDTO.getCity());
        assertEquals("country", "country" + id, orderDTO.getCountry());
        assertEquals("state", "state" + id, orderDTO.getState());
        assertEquals("street1", "street1" + id, orderDTO.getStreet1());
        assertEquals("street2", "street2" + id, orderDTO.getStreet2());
        assertEquals("zipcode", "zip" + id, orderDTO.getZipcode());
        assertEquals("CreditCardExpiryDate", "10/18", orderDTO.getCreditCardExpiryDate());
        assertEquals("CreditCardNumber", "4564 1231 4564 1222", orderDTO.getCreditCardNumber());
        assertEquals("CreditCardType", "Visa", orderDTO.getCreditCardType());
        assertEquals("order items", 2, orderDTO.getOrderLines().size());
        OrderLineDTO firstOrderLineDTO = ((OrderLineDTO)orderDTO.getOrderLines().iterator().next());
        assertEquals("First OrderLine quantity", Integer.parseInt(id), firstOrderLineDTO.getQuantity());
    }

    private String getUniqueId(final String domainClassName) throws RemoteException {
		return getPossibleUniqueStringId();
		// return getOrderService().getUniqueId(domainClassName);
	}
}
