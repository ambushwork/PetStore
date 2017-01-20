package com.yaps.petstore.server.domain;

import java.util.List;
import org.junit.*;

import static org.junit.Assert.*;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import junit.framework.JUnit4TestAdapter;

import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.domain.product.Product;



/**
 * This class tests some JPA NamedQueries
 */
public final class JPANamedQueriesTJU4 {
    private static String _persistenceUnitName = "petstorePU";
    private static EntityManagerFactory _emf;
    private static EntityManager _em;
    private static EntityTransaction _tx;

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JPANamedQueriesTJU4.class);
    }

    @BeforeClass
    public static void initEntityManager() throws Exception {
        _emf = Persistence.createEntityManagerFactory(_persistenceUnitName);
        _em = _emf.createEntityManager();
    }

    @AfterClass
    public static void closeEntityManager() {
        _em.close();
        _emf.close();
    }

    @Before
    public void initTransaction() {
        _tx = _em.getTransaction();
    }
    
    //==================================
    //=            Test cases          =
    //==================================

    /**
     * This test ensures that the named query "Product.findAllInCategory" works.
     */
    @Test
    public void testDomainFindAllProducts() throws Exception {
    	Query query = _em.createNamedQuery("Product.findAll");
    	List<Product> entities = query.getResultList();
        assertFalse(entities.isEmpty());
        Product product = entities.get(0);
        assertEquals("Amazon Parrot", product.getName());
    }

    /**
     * This test ensures that the named query "Product.findAllInCategory" works.
     */
    @Test
    public void testDomainFindAllProductsInDogs() throws Exception {
    	Query query = _em.createNamedQuery("Product.findAllInCategory");
    	String categoryId = "DOGS";
    	query.setParameter("categoryId", categoryId);
    	List<Product> entities = query.getResultList();
        assertFalse(entities.isEmpty());
        Product product = entities.get(0);
        assertEquals("Bulldog", product.getName());
    }

    @Test
    public void createCategory() throws Exception {
    	String id = null;
        Category category = new Category(null, "RABBITS", "A description here");
        _tx.begin();
        category.setId(id);
        _em.persist(category);
        _tx.commit();
        assertNotNull("ID should not be null", category.getId());
    }
}