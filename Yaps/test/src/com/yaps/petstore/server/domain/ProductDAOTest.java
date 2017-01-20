package com.yaps.petstore.server.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.domain.category.CategoryDAO;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.server.domain.product.Product;
import com.yaps.petstore.server.domain.product.ProductDAO;
import com.yaps.petstore.common.exception.*;

import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class tests the ProductDAO class
 */
public final class ProductDAOTest extends AbstractTestCase {

    private final ProductDAO _dao = new ProductDAO();
    private final CategoryDAO _categoryDAO = new CategoryDAO();

    public ProductDAOTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(ProductDAOTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testDomainFindProductWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = getUniqueId();
        try {
            findProduct(id);
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
    public void testDomainFindAllProducts() throws Exception {
        final String id = getUniqueId();

        // First findAll
        final int firstSize = findAllProducts();

        // Create an object
        createProduct(id);

        // Ensures that the object exists
        try {
            findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllProducts();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllProductsForACategory() throws Exception {
    	Category newCategory = createNewCategory();
    	final String categoryId = newCategory.getId();

        // First findAll
        final int firstSize = findAllProducts(categoryId);

        // Checks that the collection is empty
        if (firstSize != 0) fail("The collection should be empty");

        // Create an object
        Product product = createProductForCategory(newCategory);

        // Ensures that the object exists
        try {
            findProduct(product.getId());
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllProducts(categoryId);

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeProduct(product.getId());

    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateProduct() throws Exception {
        final String id = getUniqueId();
        Product product = null;

        // Ensures that the object doesn't exist
        try {
            product = findProduct(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProduct(product, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createProduct(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateProductWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Product product = new Product(new String(), new String(), new String(), null);
            product.checkData();
            _dao.insert(product);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final Product product = new Product(null, null, null, null);
            product.checkData();
            _dao.insert(product);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testDomainCreateProductWithInvalidCategory() throws Exception {
        final String id = getUniqueId();

        // Creates an object with no object linked
        try {
            final Product product = new Product(id, "name" + id, "description" + id, null);
            product.checkData();
            _dao.insert(product);
            fail("Object with no object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with a null linked object
        try {
            final Product product = new Product(id, "name" + id, "description" + id, null);
            product.checkData();
            _dao.insert(product);
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            final Product product = new Product(id, "name" + id, "description" + id, new Category());
            product.checkData();
            _dao.insert(product);
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateProductWithInvalidValues() throws Exception {

        // Creates an object
        final String id = getUniqueId();
        createProduct(id);

        // Ensures that the object exists
        Product product = null;
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            product.setName(new String());
            product.setDescription(new String());
            product.checkData();
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            product.setName(null);
            product.setDescription(null);
            product.checkData();
            fail("Updating an object with null values should break");
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Updates the object with valid values before removing it
        // since remove will call checkData()!
        product.setName("x");
        product.setDescription("x");
        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail();
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateProduct() throws Exception {
        final String id = getUniqueId();

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        Product product = null;
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProduct(product, id);

        // Updates the object with new values
        updateProduct(product, id + 1);

        // Ensures that the object still exists
        Product productUpdated = null;
        try {
            productUpdated = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkProduct(productUpdated, id + 1);

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Product findProduct(final String id) throws FinderException, CheckException {
        final Product product = (Product)_dao.findByPrimaryKey(id);
        return product;
    }

    private int findAllProducts() throws FinderException {
        try {
            return _dao.selectAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private int findAllProducts(String categoryId) throws ObjectNotFoundException {
        try {
            return _dao.findAllInCategory(categoryId).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

     // Creates a category first and then a product linked to this category
    private void createProduct(final String id) throws CreateException, CheckException, ObjectNotFoundException {
        // Create Category
        final String newCategoryId = _categoryDAO.getUniqueId();
        final Category category = new Category(newCategoryId, "name" + newCategoryId, "description" + newCategoryId);
        _categoryDAO.insert(category);
        // Create Product
        final Product product = new Product(id, "name" + id, "description" + id, category);
        try {
        	_dao.insert(product);
        } catch ( DuplicateKeyException e ) {
        	// remove the added category object
        	_categoryDAO.remove(newCategoryId);
        	// rethrow the exception
        	throw e;
        }
    }

    // Creates a category and updates the product with this new category
    private void updateProduct(final Product product, final String id) throws UpdateException, CreateException, ObjectNotFoundException {
    	String oldCategoryId = product.getCategory().getId();
        // Create Category
        final String newCategoryId = _categoryDAO.getUniqueId();
        final Category category = new Category(newCategoryId, "name" + newCategoryId, "description" + newCategoryId);
        _categoryDAO.insert(category);
        // Update Product with new category
        product.setName("name" + id);
        product.setDescription("description" + id);
        product.setCategory(category);
        _dao.update(product);
        // remove old test category
        _categoryDAO.remove(oldCategoryId);
    }

    private void removeProduct(final String id) throws RemoveException, ObjectNotFoundException {
        final String productId = id;
        Product product = (Product)_dao.findByPrimaryKey(productId);
        final String categoryId = product.getCategory().getId();
        _dao.remove(productId);
        _categoryDAO.remove(categoryId);
    }

    private void checkProduct(final Product product, final String id) {
        assertEquals("name", "name" + id, product.getName());
        assertEquals("description", "description" + id, product.getDescription());
        assertNotNull("category", product.getCategory());
    }

    // Creates a new category and return it
    private Category createNewCategory() throws CreateException, CheckException {
        final String newCategoryId = _categoryDAO.getUniqueId();
        final Category category = new Category("cat" + newCategoryId, "name" + newCategoryId, "description" + newCategoryId);
        _categoryDAO.insert(category);
        return category;
    }

    // Creates a product linked to an existing category
    private Product createProductForCategory(final Category category) throws CreateException, CheckException {
        final String id = getUniqueId();
        final Product product = new Product(id, "name" + id, "description" + id, category);
        _dao.insert(product);
        return product;
    }

    protected String getUniqueId() {
    	String id = _dao.getUniqueId();
    	return id;
    }
}
