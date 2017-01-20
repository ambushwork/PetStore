package com.yaps.petstore.server.service;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.dto.CategoryDTO;
import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.dto.ProductDTO;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.common.locator.ServiceLocator;

import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.server.domain.product.Product;
import com.yaps.petstore.server.service.catalog.CatalogService;
import com.yaps.petstore.server.service.catalog.CatalogServiceHome;
import junit.framework.TestSuite;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class tests the CatalogService class
 */
public final class CatalogServiceTest extends AbstractTestCase {

    public CatalogServiceTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CatalogServiceTest.class);
    }

    //==================================
    //=   Test cases for Category      =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testServiceFindCategoryWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();

        // Finds an object with a unknown identifier
        final String id = getUniqueId("Category");
        try {
            service.findCategory(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            service.findCategory(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            service.findCategory(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testServiceFindAllCategories() throws Exception {
        final String id = getUniqueId("Category");

        // First findAll
        final int firstSize = findAllCategories();

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        try {
            findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllCategories();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testServiceCreateCategory() throws Exception {
        final String id = getUniqueId("Category");
        CategoryDTO categoryDTO = null;

        // Ensures that the object doesn't exist
        try {
            findCategory(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        try {
            categoryDTO = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCategory(categoryDTO, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCategory(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        deleteCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServiceCreateCategoryWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        CategoryDTO categoryDTO;

        // Creates an object with a null parameter
        try {
            service.createCategory(null);
            fail("Object with null parameter should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with empty values
        try {
            categoryDTO = new CategoryDTO(new String(), new String(), new String());
            service.createCategory(categoryDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            categoryDTO = new CategoryDTO(null, null, null);
            service.createCategory(categoryDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testServiceUpdateCategory() throws Exception {
        final String id = getUniqueId("Category");
        final String updatePattern = id + "_updated";

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        CategoryDTO categoryDTO = null;
        try {
            categoryDTO = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCategory(categoryDTO, id);

        // Updates the object with new values
        updateCategory(categoryDTO, updatePattern);

        // Ensures that the object still exists
        CategoryDTO categoryUpdated = null;
        try {
            categoryUpdated = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCategory(categoryUpdated, updatePattern);

        // Cleans the test environment
        deleteCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testServiceUpdateCategoryWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        CategoryDTO categoryDTO;

        // Updates an object with a null parameter
        try {
            service.updateCategory(null);
            fail("Object with null parameter should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with empty values
        try {
            categoryDTO = new CategoryDTO(new String(), new String(), new String());
            service.updateCategory(categoryDTO);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
            categoryDTO = new CategoryDTO(null, null, null);
            service.updateCategory(categoryDTO);
            fail("Object with null values should not be updated");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteUnknownCategory() throws Exception {
        final String id = getUniqueId("Category");

        // Ensures that the object doesn't exist
        try {
            findCategory(id);
            fail("Object has not been created it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Delete the unknown object
        try {
            deleteCategory(id);
            fail("Deleting an unknown object should break");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteInvalidCategory() throws Exception {

        // Deletes an object with null id
        try {
            deleteCategory(null);
            fail("Object with null id should not be deleted");
        } catch (CheckException e) {
        }

        // Deletes an object with null id
        try {
            deleteCategory(new String());
            fail("Object with empty id should not be deleted");
        } catch (CheckException e) {
        }
    }
    
    //==================================
    //=    Test cases for product      =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testServiceFindProductWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();

        // Finds an object with a unknown identifier
        final String id = getUniqueId("Product");
        try {
            service.findProduct(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            service.findProduct(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            service.findProduct(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testServiceFindAllProducts() throws Exception {
        final String id = getUniqueId("Product");

        // First findAll
        final int firstSize = findAllProducts();

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        try {
            findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllProducts();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteProduct(id);

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
    public void testServiceFindAllProductsForACategory() throws Exception {
    	CategoryDTO newCategory = createNewCategory();
    	final String categoryId = newCategory.getId();

        // First findAll
        final int firstSize = findAllProducts(categoryId);

        // Checks that the collection is empty
        if (firstSize != 0) fail("The collection should be empty");

        // Create an object
        ProductDTO product = createProductForCategory(newCategory);

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
        deleteProduct(product.getId());
    }

    /**
     * This test tries to find a list of objects with a invalid identifier.
     */
    public void testServiceFindAllProductsForACategoryWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();

        // Finds a list of object with a unknown identifier
        final String id = getUniqueId("Product");
        try {
            service.findProducts(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds a list of object with an empty identifier
        try {
            service.findProducts(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds a list of object with a null identifier
        try {
            service.findProducts(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testServiceCreateProduct() throws Exception {
        final String id = getUniqueId("Product");
        ProductDTO productDTO = null;

        // Ensures that the object doesn't exist
        try {
            findProduct(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        try {
            productDTO = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProduct(productDTO, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createProduct(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        deleteProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServiceCreateProductWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        ProductDTO productDTO;

        // Creates an object with a null parameter
        try {
            service.createProduct(null);
            fail("Object with null parameter should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with empty values
        try {
            productDTO = new ProductDTO(new String(), new String(), new String());
            service.createProduct(productDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            productDTO = new ProductDTO(null, null, null);
            service.createProduct(productDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testServiceCreateProductWithInvalidCategory() throws Exception {
        final String id = getUniqueId("Product");
        final CatalogService service = getCatalogService();
        ProductDTO product;

        // Creates an object with no object linked
        try {
            product = new ProductDTO(id, "name" + id, "description" + id);
            service.createProduct(product);
            fail("Object with no object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with a null linked object
        try {
            product = new ProductDTO(id, "name" + id, "description" + id);
            product.setCategoryId(null);
            service.createProduct(product);
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            product = new ProductDTO(id, "name" + id, "description" + id);
            product.setCategoryId(new String());
            service.createProduct(product);
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an unknown linked object
        try {
            product = new ProductDTO(id, "name" + id, "description" + id);
            product.setCategoryId(id);
            service.createProduct(product);
            fail("Object with an unknown object linked should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testServiceUpdateProduct() throws Exception {
        final String id = getUniqueId("Product");
        final String updatePattern = id + "_updated";

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        ProductDTO productDTO = null;
        try {
            productDTO = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProduct(productDTO, id);

        // Updates the object with new values
        updateProduct(productDTO, updatePattern);

        // Ensures that the object still exists
        ProductDTO productUpdated = null;
        try {
            productUpdated = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkProduct(productUpdated, updatePattern);

        // Cleans the test environment
        deleteProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testServiceUpdateProductWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        ProductDTO productDTO;

        // Updates an object with a null parameter
        try {
            service.updateProduct(null);
            fail("Object with null parameter should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with empty values
        try {
            productDTO = new ProductDTO(new String(), new String(), new String());
            service.updateProduct(productDTO);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
            productDTO = new ProductDTO(null, null, null);
            service.updateProduct(productDTO);
            fail("Object with null values should not be updated");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteUnknownProduct() throws Exception {
        final String id = getUniqueId("Product");

        // Ensures that the object doesn't exist
        try {
            findProduct(id);
            fail("Object has not been created it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Delete the unknown object
        try {
            deleteProduct(id);
            fail("Deleting an unknown object should break");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteInvalidProduct() throws Exception {

        // Deletes an object with null id
        try {
            deleteProduct(null);
            fail("Object with null id should not be deleted");
        } catch (CheckException e) {
        }

        // Deletes an object with null id
        try {
            deleteProduct(new String());
            fail("Object with empty id should not be deleted");
        } catch (CheckException e) {
        }
    }

    //==================================
    //=      Test cases for item       =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testServiceFindItemWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();

        // Finds an object with a unknown identifier
        final String id = getUniqueId("Item");
        try {
            service.findItem(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            service.findItem(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            service.findItem(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testServiceFindAllItems() throws Exception {
        final String id = getUniqueId("Item");

        // First findAll
        final int firstSize = findAllItems();

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllItems();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    public void testServiceFindAllItemsForAmazonParrot() throws Exception {
    	final String productId = "AVCB01";
    	final String name ="Amazon Parrot";
    	final String description = "Great companion for up to 75 years";
    	ProductDTO productDTO = findProduct(productId);
    	assertEquals(name, productDTO.getName());
    	assertEquals(description, productDTO.getDescription());
    	Collection itemsDTO = getAllItemsForProduct(productId);
        for (Iterator iterator = itemsDTO.iterator(); iterator.hasNext();) {
            final ItemDTO itemDTO = (ItemDTO) iterator.next();
        	assertNotNull(itemDTO.getProductDescription());
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testServiceFindAllItemsForAProduct() throws Exception {
    	ProductDTO newProduct = createNewProduct();
    	final String productId = newProduct.getId();

        // First findAll
        final int firstSize = findAllItems(productId);

        // Checks that the collection is empty
        if (firstSize != 0) fail("The collection should be empty");

        // Create an object
        ItemDTO item = createItemForProduct(newProduct);

        // Ensures that the object exists
        try {
            findItem(item.getId());
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllItems(productId);

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteItem(item.getId());
    }

    /**
     * This test tries to find a list of objects with a invalid identifier.
     */
    public void testServiceFindAllItemsForAProductWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();

        // Finds a list of object with a unknown identifier
        final String id = getUniqueId("Item");
        try {
            service.findItems(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds a list of object with an empty identifier
        try {
            service.findItems(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds a list of object with a null identifier
        try {
            service.findItem(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method search works. It does a first search, creates
     * a new object and does a second search.
     */
    public void testServiceSearchItems() throws Exception {
        final String id = getUniqueId("Item");

        // First search
        final int firstSize = searchItems(id);

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second search
        final int secondSize = searchItems(id);

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteItem(id);

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
    public void testServiceCreateItem() throws Exception {
        final String id = getUniqueId("Item");
        ItemDTO itemDTO = null;

        // Ensures that the object doesn't exist
        try {
            findItem(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            itemDTO = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkItem(itemDTO, id);

        // Cleans the test environment
        deleteItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServiceCreateItemWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        ItemDTO itemDTO;

        // Creates an object with a null parameter
        try {
            service.createItem(null);
            fail("Object with null parameter should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with empty values
        try {
            itemDTO = new ItemDTO(new String(), new String(), 0);
            service.createItem(itemDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            itemDTO = new ItemDTO(null, null, 0);
            service.createItem(itemDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testServiceCreateItemWithInvalidProduct() throws Exception {
        final CatalogService service = getCatalogService();
        final String id = getUniqueId("Item");
        ItemDTO item;

        // Creates an object with no object linked
        try {
            item = new ItemDTO(id, "name" + id, 0);
            service.createItem(item);
            fail("Object with no object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with a null linked object
        try {
            item = new ItemDTO(id, "name" + id, 0);
            item.setProductId(null);
            service.createItem(item);
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            item = new ItemDTO(id, "name" + id, 0);
            item.setProductId(new String());
            service.createItem(item);
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        } catch (CreateException e) {
        }

        // Creates an object with an unknown linked object
        try {
            item = new ItemDTO(id, "name" + id, 0);
        	final String productId = getUniqueId("Product");
            item.setProductId(productId);
            service.createItem(item);
            fail("Object with an unknown object linked should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testServiceUpdateItem() throws Exception {
        final String id = getUniqueId("Item");
        final String updatePattern = id + "_updated";

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        ItemDTO itemDTO = null;
        try {
            itemDTO = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkItem(itemDTO, id);

        // Updates the object with new values
        updateItem(itemDTO, updatePattern);

        // Ensures that the object still exists
        ItemDTO itemUpdated = null;
        try {
            itemUpdated = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkItem(itemUpdated, updatePattern);

        // Cleans the test environment
        deleteItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testServiceUpdateItemWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        ItemDTO itemDTO;

        // Updates an object with a null parameter
        try {
            service.updateItem(null);
            fail("Object with null parameter should not be updated");
        } catch (UpdateException e) {
        }

        // Updates an object with empty values
        try {
            itemDTO = new ItemDTO(new String(), new String(), 0);
            service.updateItem(itemDTO);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
            itemDTO = new ItemDTO(null, null, 0);
            service.updateItem(itemDTO);
            fail("Object with null values should not be updated");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteUnknownItem() throws Exception {
        final String id = getUniqueId("Item");

        // Ensures that the object doesn't exist
        try {
            findItem(id);
            fail("Object has not been created it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Delete the unknown object
        try {
            deleteItem(id);
            fail("Deleting an unknown object should break");
        } catch (ObjectNotFoundException e) {
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteInvalidItem() throws Exception {

        // Deletes an object with null id
        try {
            deleteItem(null);
            fail("Object with null id should not be deleted");
        } catch (CheckException e) {
        }

        // Deletes an object with null id
        try {
            deleteItem(new String());
            fail("Object with empty id should not be deleted");
        } catch (CheckException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
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
    //=  Private Methods for Category  =
    //==================================
    private CategoryDTO findCategory(final String id) throws FinderException, CheckException, RemoteException {
        final CategoryDTO categoryDTO = getCatalogService().findCategory(id);
        return categoryDTO;
    }

    private int findAllCategories() throws FinderException, RemoteException {
        try {
            return getCatalogService().findCategories().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private void createCategory(final String id) throws CreateException, CheckException, RemoteException {
        final CategoryDTO categoryDTO = new CategoryDTO(id, "name" + id, "description" + id);
        getCatalogService().createCategory(categoryDTO);
    }

    private void updateCategory(final CategoryDTO categoryDTO, final String updatePattern) throws UpdateException, CheckException, RemoteException {
        categoryDTO.setName("name" + updatePattern);
        categoryDTO.setDescription("description" + updatePattern);
        getCatalogService().updateCategory(categoryDTO);
    }

    private void deleteCategory(final String id) throws RemoveException, CheckException, RemoteException {
        getCatalogService().deleteCategory(id);
    }

    private void checkCategory(final CategoryDTO categoryDTO, final String id) {
        assertEquals("name", "name" + id, categoryDTO.getName());
        assertEquals("description", "description" + id, categoryDTO.getDescription());
    }

    //==================================
    //=  Private Methods for Product   =
    //==================================
    private ProductDTO findProduct(final String id) throws FinderException, CheckException, RemoteException {
        final ProductDTO productDTO = getCatalogService().findProduct(id);
        return productDTO;
    }

    private int findAllProducts() throws FinderException, RemoteException {
        try {
            return getCatalogService().findProducts().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private int findAllProducts(String categoryId) throws FinderException, RemoteException, CheckException {
        final CatalogService service = getCatalogService();
        try {
            return service.findProducts(categoryId).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first and then a product linked to this category
    private void createProduct(final String id) throws CreateException, CheckException, RemoteException {
        // Create Category
    	final String categoryId = getUniqueId("Category");
    	final CategoryDTO categoryDTO = new CategoryDTO(categoryId, "name" + categoryId, "description" + categoryId);
        getCatalogService().createCategory(categoryDTO);
        // Create Product
        final ProductDTO productDTO = new ProductDTO(id, "name" + id, "description" + id);
        productDTO.setCategoryId(categoryId);
        getCatalogService().createProduct(productDTO);
    }

    // Creates a category and updates the product with this new category
    private void updateProduct(final ProductDTO productDTO, final String id) throws UpdateException, CheckException, RemoteException, CreateException {
        // Create Category
    	final String categoryId = getUniqueId("Category");
        final CategoryDTO categoryDTO = new CategoryDTO(categoryId, "name" + categoryId, "description" + categoryId);
        getCatalogService().createCategory(categoryDTO);
        // Update Product with new category
        productDTO.setName("name" + id);
        productDTO.setDescription("description" + id);
        productDTO.setCategoryId(categoryId);
        getCatalogService().updateProduct(productDTO);
    }

    private void deleteProduct(final String id) throws RemoveException, CheckException, RemoteException, FinderException {
    	final String productId = id;
    	final ProductDTO productDTO = getCatalogService().findProduct(productId);
    	final String categoryId = productDTO.getCategoryId();
    	getCatalogService().deleteProduct(productId);
        getCatalogService().deleteCategory(categoryId);
    }

    private void checkProduct(final ProductDTO productDTO, final String id) {
        assertEquals("name", "name" + id, productDTO.getName());
        assertEquals("description", "description" + id, productDTO.getDescription());
    }

    // Creates a new category and return it
    private CategoryDTO createNewCategory() throws CreateException, CheckException, RemoteException {
    	final String categoryId = getUniqueId("Category");
        final CategoryDTO categoryDTO = new CategoryDTO(categoryId, "name" + categoryId, "description" + categoryId);
        getCatalogService().createCategory(categoryDTO);
        return categoryDTO;
    }

    // Creates a product linked to an existing category
    private ProductDTO createProductForCategory(final CategoryDTO category) throws CreateException, CheckException, RemoteException {
        final String id = getUniqueId("Product");
        final ProductDTO productDTO = new ProductDTO(id, "name" + id, "description" + id);
        productDTO.setCategoryId(category.getId());
        getCatalogService().createProduct(productDTO);
        return productDTO;
    }
    
    //==================================
    //=    Private Methods for Item    =
    //==================================
    private ItemDTO findItem(final String id) throws CheckException, FinderException, RemoteException {
        final ItemDTO itemDTO = getCatalogService().findItem(id);
        return itemDTO;
    }

    private int findAllItems() throws FinderException, RemoteException {
        try {
            return getCatalogService().findItems().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private int findAllItems(String productId) throws FinderException, RemoteException, CheckException {
        final CatalogService service = getCatalogService();
        try {
            return service.findItems(productId).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private Collection getAllItemsForProduct(String productId) throws FinderException, RemoteException, CheckException {
        final CatalogService service = getCatalogService();
        try {
            return service.findItems(productId);
        } catch (ObjectNotFoundException e) {
            return null;
        }
    }

    private int searchItems(String keyword) throws FinderException, RemoteException {
        final CatalogService service = getCatalogService();
        try {
            return service.searchItems(keyword).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first, then a product and then an item linked to this product
    private void createItem(final String id) throws CreateException, CheckException, RemoteException {
        // Create Category
    	final String categoryId = getUniqueId("Category");
        final CategoryDTO categoryDTO = new CategoryDTO(categoryId, "name" + categoryId, "description" + categoryId);
        getCatalogService().createCategory(categoryDTO);
        // Create Product
    	final String productId = getUniqueId("Product");
        final ProductDTO productDTO = new ProductDTO(productId, "name" + productId, "description" + productId);
        productDTO.setCategoryId(categoryId);
        getCatalogService().createProduct(productDTO);
        // Create Item
        final ItemDTO itemDTO = new ItemDTO(id, "name" + id, Double.parseDouble(id));
        itemDTO.setImagePath("imagePath" + id);
        itemDTO.setProductId(productId);
        getCatalogService().createItem(itemDTO);
    }

    // Creates a category, a product and updates the item with this new product
    private void updateItem(final ItemDTO itemDTO, final String updatePattern) throws UpdateException, CheckException, RemoteException, CreateException {
        // Create Category
    	final String categoryId = getUniqueId("Category");
        final CategoryDTO categoryDTO = new CategoryDTO(categoryId, "name" + categoryId, "description" + categoryId);
        getCatalogService().createCategory(categoryDTO);
        // Create Product
    	final String productId = getUniqueId("Product");
        final ProductDTO productDTO = new ProductDTO(productId, "name" + productId, "description" + productId);
        productDTO.setCategoryId(categoryId);
        getCatalogService().createProduct(productDTO);
        // Updates the item
        itemDTO.setName("name" + updatePattern);
        itemDTO.setImagePath("imagePath" + updatePattern);
        itemDTO.setProductId(productId);
        getCatalogService().updateItem(itemDTO);
    }

    private void deleteItem(final String id) throws RemoveException, CheckException, RemoteException, FinderException {
    	final ItemDTO itemDTO = getCatalogService().findItem(id);
    	final String productId = itemDTO.getProductId();
    	final ProductDTO productDTO = getCatalogService().findProduct(productId);
    	final String categoryId = productDTO.getCategoryId();
        getCatalogService().deleteItem(id);
        getCatalogService().deleteProduct(productId);
        getCatalogService().deleteCategory(categoryId);
    }

    private void checkItem(final ItemDTO itemDTO, final String id) {
        assertEquals("name", "name" + id, itemDTO.getName());
        assertEquals("imagePath", "imagePath" + id, itemDTO.getImagePath());
        assertNotNull(itemDTO.getProductId());
        assertNotNull(itemDTO.getProductName());
        assertNotNull(itemDTO.getProductDescription());
        }

    // Creates a category first, then a product and return it
    private ProductDTO createNewProduct() throws CreateException, CheckException, RemoteException {
        // Create Category
    	final String categoryId = getUniqueId("Category");
        final CategoryDTO categoryDTO = new CategoryDTO(categoryId, "name" + categoryId, "description" + categoryId);
        getCatalogService().createCategory(categoryDTO);
        // Create Product
    	final String productId = getUniqueId("Product");
        final ProductDTO productDTO = new ProductDTO(productId, "name" + productId, "description" + productId);
        productDTO.setCategoryId(categoryId);
        getCatalogService().createProduct(productDTO);
        return productDTO;
    }

    // Creates an item linked to an existing product
    private ItemDTO createItemForProduct(final ProductDTO product) throws CreateException, CheckException, RemoteException {
        final String id = getUniqueId("Item");
        final ItemDTO itemDTO = new ItemDTO(id, "name" + id, Double.parseDouble(id));
        itemDTO.setImagePath("imagePath" + id);
        itemDTO.setProductId(product.getId());
        getCatalogService().createItem(itemDTO);
        return itemDTO;
    }

    private String getUniqueId(final String domainClassName) throws RemoteException {
		// return getPossibleUniqueStringId();
		return getCatalogService().getUniqueId(domainClassName);
	}

}

