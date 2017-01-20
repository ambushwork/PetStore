package com.yaps.petstore.common.delegate;

import com.yaps.petstore.common.dto.CategoryDTO;
import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.dto.ProductDTO;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.common.locator.ServiceLocator;
import com.yaps.petstore.server.service.catalog.CatalogServiceHome;
import com.yaps.petstore.server.service.catalog.CatalogService;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 * This class follows the Delegate design pattern. It's a one to one method
 * with the CatalogService class. Each method delegates the call to the
 * CatalogService class
 */
public final class CatalogDelegate {

    // ======================================
    // =             Attributes             =
    // ======================================

    // ======================================
    // =      Category Business methods     =
    // ======================================
    /**
     * Delegates the call to the {@link CatalogService#createCategory(CategoryDTO) CatalogService().createCategory} method.
     */
    public static CategoryDTO createCategory(final CategoryDTO categoryDTO) throws CreateException, CheckException, RemoteException {
        return getCatalogService().createCategory(categoryDTO);
    }

    /**
     * Delegates the call to the {@link CatalogService#findCategory(String) CatalogService().findCategory} method.
     */
    public static CategoryDTO findCategory(final String categoryId) throws FinderException, CheckException, RemoteException {
        return getCatalogService().findCategory(categoryId);
    }

    /**
     * Delegates the call to the {@link CatalogService#deleteCategory(String) CatalogService().deleteCategory} method.
     */
    public static void deleteCategory(final String categoryId) throws RemoveException, CheckException, RemoteException {
        getCatalogService().deleteCategory(categoryId);
    }

    /**
     * Delegates the call to the {@link CatalogService#updateCategory(CategoryDTO) CatalogService().updateCategory} method.
     */
    public static void updateCategory(final CategoryDTO categoryDTO) throws UpdateException, CheckException, RemoteException {
        getCatalogService().updateCategory(categoryDTO);
    }

    /**
     * Delegates the call to the {@link CatalogService#findCategories() CatalogService().findCategories} method.
     */
    public static Collection findCategories() throws FinderException, RemoteException {
        return getCatalogService().findCategories();
    }

    // ======================================
    // =      Product Business methods     =
    // ======================================
    /**
     * Delegates the call to the {@link CatalogService#createProduct(ProductDTO) CatalogService().createProduct} method.
     */
    public static ProductDTO createProduct(final ProductDTO productDTO) throws CreateException, CheckException, RemoteException {
        return getCatalogService().createProduct(productDTO);
    }

    /**
     * Delegates the call to the {@link CatalogService#findProduct(String) CatalogService().findProduct} method.
     */
    public static ProductDTO findProduct(final String productId) throws FinderException, CheckException, RemoteException {
        return getCatalogService().findProduct(productId);
    }

    /**
     * Delegates the call to the {@link CatalogService#deleteProduct(String) CatalogService().deleteProduct} method.
     */
    public static void deleteProduct(final String productId) throws RemoveException, CheckException, RemoteException {
        getCatalogService().deleteProduct(productId);
    }

    /**
     * Delegates the call to the {@link CatalogService#updateProduct(ProductDTO) CatalogService().updateProduct} method.
     */
    public static void updateProduct(final ProductDTO productDTO) throws UpdateException, CheckException, RemoteException {
        getCatalogService().updateProduct(productDTO);
    }

    /**
     * Delegates the call to the {@link CatalogService#findProducts() CatalogService().findProducts} method.
     */
    public static Collection findProducts() throws FinderException, RemoteException {
        return getCatalogService().findProducts();
    }

    /**
     * Delegates the call to the {@link CatalogService#findProducts(String) CatalogService().findProducts} method.
     */
    public static Collection findProducts(final String categoryId) throws FinderException, CheckException, RemoteException {
        return getCatalogService().findProducts(categoryId);
    }

    // ======================================
    // =        Item Business methods       =
    // ======================================
    /**
     * Delegates the call to the {@link CatalogService#createItem(ItemDTO) CatalogService().createItem} method.
     */
    public static ItemDTO createItem(final ItemDTO item) throws CreateException, CheckException, RemoteException {
        return getCatalogService().createItem(item);
    }

    /**
     * Delegates the call to the {@link CatalogService#findItem(String) CatalogService().findItem} method.
     */
    public static ItemDTO findItem(final String itemId) throws FinderException, CheckException, RemoteException {
        return getCatalogService().findItem(itemId);
    }

    /**
     * Delegates the call to the {@link CatalogService#deleteItem(String) CatalogService().deleteItem} method.
     */
    public static void deleteItem(final String itemId) throws RemoveException, CheckException, RemoteException {
        getCatalogService().deleteItem(itemId);
    }

    /**
     * Delegates the call to the {@link CatalogService#updateItem(ItemDTO) CatalogService().updateItem} method.
     */
    public static void updateItem(final ItemDTO item) throws UpdateException, CheckException, RemoteException {
        getCatalogService().updateItem(item);
    }

    /**
     * Delegates the call to the {@link CatalogService#findItems() CatalogService().findItems} method.
     */
    public static Collection findItems() throws FinderException, RemoteException {
        return getCatalogService().findItems();
    }

    /**
     * Delegates the call to the {@link CatalogService#findItems(String) CatalogService().findItems} method.
     */
    public static Collection findItems(final String productId) throws FinderException, CheckException, RemoteException {
        return getCatalogService().findItems(productId);
    }

    /**
     * Delegates the call to the {@link CatalogService#searchItems(String) CatalogService().searchItems} method.
     */
    public static Collection searchItems(final String keyword) throws FinderException, RemoteException {
        return getCatalogService().searchItems(keyword);
    }

    // ======================================
    // =            Private methods         =
    // ======================================
    private static CatalogService getCatalogService() throws RemoteException {
        CatalogService catalogServiceRemote = null;
        try {
            // Looks up for the home interface
            catalogServiceRemote = (CatalogService) ServiceLocator.getInstance().getHome(CatalogServiceHome.JNDI_NAME);
        } catch (Exception e) {
            throw new RemoteException("Lookup or Create exception", e);
        }
        return catalogServiceRemote;
    }
}
