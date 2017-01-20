package com.yaps.petstore.server.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.domain.category.CategoryDAO;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.server.domain.item.ItemDAO;
import com.yaps.petstore.server.domain.product.Product;
import com.yaps.petstore.server.domain.product.ProductDAO;
import com.yaps.petstore.common.exception.*;

import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class tests the ItemDAO class
 */
public final class ItemDAOTest extends AbstractTestCase {

    private final ItemDAO _dao = new ItemDAO();
    private final CategoryDAO _categoryDAO = new CategoryDAO();
    private final ProductDAO _productDAO = new ProductDAO();
    private final double _defaultUnitCost = 1.2345;

    public ItemDAOTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(ItemDAOTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testDomainFindItemWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = getUniqueId();
        try {
            findItem(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            _dao.findByPrimaryKey(new String());
            fail("Object with empty id should not be found");
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
    public void testDomainFindAllItems() throws Exception {
        final String id = getUniqueId();

        // First findAll
        final int firstSize = findAllItems();

        // Create an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllItems();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllItemsForAProduct() throws Exception {

    	Product newProduct = createNewProduct();
    	final String productId = newProduct.getId();

        // First findAll
        final int firstSize = findAllItems(productId);

        // Checks that the collection is empty
        if (firstSize != 0) fail("The collection should be empty");

        // Create an object
        Item item1 = createItemForProduct(newProduct);

        // Ensures that the object exists
        try {
            findItem(item1.getId());
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllItems(productId);

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Create an new object with a different id
        Item item2 = createItemForProduct(newProduct);

        // Ensures that the new object exists
        try {
            findItem(item2.getId());
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // third findAll
        final int thirdSize = findAllItems(productId);

        // Checks that the collection size has increase of one
        if (thirdSize != secondSize + 1) fail("The collection should have the same size");

        // Cleans the test environment
        _dao.remove(item1.getId());
        _dao.remove(item2.getId());
        removeProduct(newProduct);
    }

    /**
     * This test ensures that the method search works. It does a first search, creates
     * a new object and does a second search.
     */
    public void testDomainSearchItems() throws Exception {
        final String id = getUniqueId();

        // First search
        final int firstSize = searchItems(id);

        // Create an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second search
        final int secondSize = searchItems(id);

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateItem() throws Exception {
        final String id = getUniqueId();
        Item item = null;

        // Ensures that the object doesn't exist
        try {
            item = findItem(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkItem(item, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createItem(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateItemWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Item item = new Item(new String(), new String(), 0, null);
            item.checkData();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final Item item = new Item(null, null, 0, null);
            item.checkData();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testDomainCreateItemWithInvalidProduct() throws Exception {
        final String id = getUniqueId();

        // Creates an object with a null linked object
        try {
            final Item item = new Item("item" + id, "name" + id, 0, null);
            item.checkData();
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            final Item item = new Item("item" + id, "name" + id, 0, new Product());
            item.checkData();
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateItemWithInvalidValues() throws Exception {

        // Creates an object
        final String id = getUniqueId();
        createItem(id);

        // Ensures that the object exists
        Item item = null;
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            item.setName(new String());
            item.setUnitCost(0);
            item.checkData();
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            item.setName(null);
            item.setUnitCost(0);
            item.checkData();
            fail("Updating an object with null values should break");
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Updates the object with valid values before removing it
        item.setName("x");
        item.setUnitCost(10);
        // since remove will call checkData()!
        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail();
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateItem() throws Exception {
        final String id = getUniqueId();

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        Item item = null;
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkItem(item, id);

        // Updates the object with new values
        updateItem(item, id + 1);

        // Ensures that the object still exists
        Item itemUpdated = null;
        try {
            itemUpdated = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkItem(itemUpdated, id + 1);

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Item findItem(final String id) throws FinderException, CheckException {
        final Item item = (Item)_dao.findByPrimaryKey(id);
        return item;
    }

    private int findAllItems() throws FinderException {
        try {
            return _dao.selectAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private int findAllItems(String productId) throws FinderException {
        try {
            return _dao.findAllInProduct(productId).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private int searchItems(String keyword) throws FinderException {
        try {
            return _dao.search(String.valueOf(keyword)).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }
    
    // Creates a category first, then a product and then an item linked to this product
    private Item createItem(final String id) throws CreateException, CheckException {
        // Create Category
        final String newCategoryId = _categoryDAO.getUniqueId();
        final Category category = new Category("cat" + newCategoryId, "name" + newCategoryId, "description" + newCategoryId);
        _categoryDAO.insert(category);
        // Create Product
        final String newProductId = _productDAO.getUniqueId();
        final Product product = new Product("prod" + newProductId, "name" + newProductId, "description" + newProductId, category);
        _productDAO.insert(product);
        // Create Item
        final Item item = new Item(id, "name" + id, _defaultUnitCost, product);
        item.setImagePath("imagePath" + id);
        try {
        	_dao.insert(item);
        } catch (DuplicateKeyException e) {
            try {
				_productDAO.remove(product.getId());
	            _categoryDAO.remove(category.getId());
			} catch (ObjectNotFoundException e1) {
			}
        	throw e;
        }
        return item;
    }

    // Creates a category, a product and updates the item with this new product
    private void updateItem(final Item item, final String id) throws UpdateException, CreateException, CheckException, ObjectNotFoundException {
    	String oldCategoryId = item.getProduct().getCategory().getId();
    	String oldProductId = item.getProduct().getId();
        // Create Category
        final String newCategoryId = _categoryDAO.getUniqueId();
        final Category category = new Category("cat" + newCategoryId, "name" + newCategoryId, "description" + newCategoryId);
        _categoryDAO.insert(category);
        // Create Product
        final String newProductId = _productDAO.getUniqueId();
        final Product product = new Product("prod" + newProductId, "name" + newProductId, "description" + newProductId, category);
        _productDAO.insert(product);
        // Updates the item
        item.setName("name" + id);
        item.setUnitCost(_defaultUnitCost);
        item.setImagePath("imagePath" + id);
        item.setProduct(product);
        _dao.update(item);
        // remove old test category and product
        _productDAO.remove(oldProductId);
        _categoryDAO.remove(oldCategoryId);
    }

    private void checkItem(final Item item, final String id) {
        assertEquals("name", "name" + id, item.getName());
        assertEquals("unitCost", new Double(_defaultUnitCost), new Double(item.getUnitCost()));
        assertNotNull("product", item.getProduct());
        assertEquals("imagePath", "imagePath" + id, item.getImagePath());
        }

    private void removeItem(final String id) throws RemoveException, CheckException, ObjectNotFoundException {
        final String itemId = id;
        Item item = (Item)_dao.findByPrimaryKey(id);
        final String productId = item.getProduct().getId();
        Product product = (Product)_productDAO.findByPrimaryKey(productId);;
        final String categoryId = product.getCategory().getId();
        Category category = (Category)_categoryDAO.findByPrimaryKey(categoryId);
        _dao.remove(itemId);
        _productDAO.remove(product.getId());
        _categoryDAO.remove(category.getId());
    }

    // Creates a category first, then a product and return it
    private Product createNewProduct() throws CreateException, CheckException {
        // Create Category
        final String newCategoryId = _categoryDAO.getUniqueId();
        final Category category = new Category("cat" + newCategoryId, "name" + newCategoryId, "description" + newCategoryId);
        _categoryDAO.insert(category);
        // Create Product
        final String newProductId = _productDAO.getUniqueId();
        final Product product = new Product("prod" + newProductId, "name" + newProductId, "description" + newProductId, category);
        _productDAO.insert(product);
        return product;
    }

    // Creates an item linked to an existing product
    private Item createItemForProduct(final Product product) throws CreateException, CheckException {
        final String id = getUniqueId();
        final Item item = new Item(id, "name" + id, _defaultUnitCost, product);
        item.setImagePath("imagePath" + id);
        _dao.insert(item);
        return item;
    }

    private void removeProduct(final Product product) throws RemoveException, CheckException, ObjectNotFoundException {
        final String categoryId = product.getCategory().getId();
        Category category = (Category)_categoryDAO.findByPrimaryKey(categoryId);
        _productDAO.remove(product.getId());
        _categoryDAO.remove(category.getId());
    }

    protected String getUniqueId() {
    	String id = _dao.getUniqueId();
    	return id;
    }
}
