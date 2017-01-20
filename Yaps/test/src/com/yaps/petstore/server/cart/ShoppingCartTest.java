package com.yaps.petstore.server.cart;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.dto.CategoryDTO;
import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.dto.ProductDTO;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.common.locator.ServiceLocator;
import com.yaps.petstore.server.service.catalog.CatalogService;
import com.yaps.petstore.server.service.catalog.CatalogServiceHome;
import junit.framework.TestSuite;

import java.rmi.RemoteException;

/**
 * This class tests the CatalogService class
 */
public final class ShoppingCartTest extends AbstractTestCase {

    public ShoppingCartTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(ShoppingCartTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testShoppingCart() throws Exception {
        final ShoppingCart shoppingCart = getShoppingCart();
        String id = getPossibleUniqueStringId();
        double total;
        ItemDTO itemDTO = null;
        ItemDTO newItemDTO = null;

        // Creates an item
        createItem(id);

        // Gets the item
        try {
            itemDTO = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Adds the item into the shopping cart [1 item]
        shoppingCart.addItem(itemDTO.getId());

        // Checks the amount of the shopping cart
        total = itemDTO.getUnitCost() * 1;
        assertEquals("The total should be equal to " + total, shoppingCart.getTotal(), new Double(total));

        // updates the quantity of the item [10 items]
        shoppingCart.updateItemQuantity(itemDTO.getId(), 10);

        // Checks the amount of the shopping cart
        total = itemDTO.getUnitCost() * 10;
        assertEquals("The total should be equal to " + total, shoppingCart.getTotal(), new Double(total));

        // Creates a new item
        id = getPossibleUniqueStringId();
        createItem(id);
        try {
            newItemDTO = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Adds the new item into the shopping cart [10 items, 1 new item]]
        shoppingCart.addItem(newItemDTO.getId());

        // Checks the amount of the shopping cart
        total = (itemDTO.getUnitCost() * 10) + newItemDTO.getUnitCost();
        assertEquals("The total should be equal to " + total, shoppingCart.getTotal(), new Double(total));

        // Removes the new item from the shopping cart [10 items]
        shoppingCart.removeItem(newItemDTO.getId());

        // Checks the amount of the shopping cart
        total = itemDTO.getUnitCost() * 10;
        assertEquals("The total should be equal to " + total, shoppingCart.getTotal(), new Double(total));

        // Empties the shopping cart [0]
        shoppingCart.empty();

        // Checks the amount of the shopping cart
        total = 0;
        assertEquals("The total should be equal to " + total, shoppingCart.getTotal(), new Double(total));

        // Cleans the test environment
        deleteItem(id);
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private ShoppingCart getShoppingCart() throws RemoteException {
        ShoppingCart shoppingCartRemote = null;
        try {
        	shoppingCartRemote = (ShoppingCart) ServiceLocator.getInstance().getHome(ShoppingCartHome.JNDI_NAME);
        } catch (Exception e) {
            throw new RemoteException("Lookup exception", e);
        }
        return shoppingCartRemote;
    }

    private CatalogService getCatalogService() throws RemoteException {
        CatalogService catalogServiceRemote = null;
        try {
        	catalogServiceRemote = (CatalogService) ServiceLocator.getInstance().getHome(CatalogServiceHome.JNDI_NAME);
        } catch (Exception e) {
            throw new RemoteException("Lookup exception", e);
        }
        return catalogServiceRemote;
    }

    //==================================
    //=    Private Methods for Item    =
    //==================================
    private ItemDTO findItem(final String id) throws CheckException, FinderException, RemoteException {
        final ItemDTO itemDTO = getCatalogService().findItem("item" + id);
        return itemDTO;
    }

    // Creates a category first, then a product and then an item linked to this product
    private void createItem(final String id) throws CreateException, CheckException, RemoteException {
        // Create Category
        final CategoryDTO categoryDTO = new CategoryDTO("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(categoryDTO);
        // Create Product
        final ProductDTO productDTO = new ProductDTO("prod" + id, "name" + id, "description" + id);
        productDTO.setCategoryId("cat" + id);
        getCatalogService().createProduct(productDTO);
        // Create Item
        final ItemDTO itemDTO = new ItemDTO("item" + id, "name" + id, Double.parseDouble(id));
        itemDTO.setImagePath("imagePath" + id);
        itemDTO.setProductId("prod" + id);
        getCatalogService().createItem(itemDTO);
    }

    private void deleteItem(final String id) throws RemoveException, CheckException, RemoteException {
        getCatalogService().deleteItem("item" + id);
        getCatalogService().deleteProduct("prod" + id);
        getCatalogService().deleteCategory("cat" + id);
    }
}

