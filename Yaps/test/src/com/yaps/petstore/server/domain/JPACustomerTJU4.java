package com.yaps.petstore.server.domain;

import org.junit.*;
import static org.junit.Assert.*;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import junit.framework.JUnit4TestAdapter;

import com.yaps.petstore.server.domain.customer.Customer;



/**
 * This class tests operations on persistent customers
 */
public final class JPACustomerTJU4 {
    private static String _persistenceUnitName = "petstorePU";
    private static EntityManagerFactory _emf;
    private static EntityManager _em;
    private static EntityTransaction _tx;
    private Customer _customer;

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JPACustomerTJU4.class);
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
    public void initTransactionAndManagedCustomer() {
        _tx = _em.getTransaction();
        _customer = new Customer(null, "Mark", "Zuckerberg");
        _tx.begin();
        _em.persist(_customer);
        _tx.commit();
    }
    
    @After
    public void removeTestedCustomer() {
    	if ( !_em.contains(_customer) )
    		return;
        _tx.begin();
        _em.remove(_customer);
        _tx.commit();
    }
    
    //==================================
    //=            Test cases          =
    //==================================
    @Test
    public void find() throws Exception {
        String id = _customer.getId();
        assertNotNull("ID should not be null", id);
        // find it from the database
        Customer customerInDB = _em.find(Customer.class, id);
        assertEquals(id, customerInDB.getId());
        assertEquals(_customer, customerInDB);
    }

    @Test
    public void update() throws Exception {
        String id = _customer.getId();
        assertNotNull("ID should not be null", id);
        String newFirstname = "Marcus";
        _customer.setFirstname(newFirstname);
        _tx.begin();
        _em.merge(_customer);
        _tx.commit();
        // find it from the database
        Customer customerInDB = _em.find(Customer.class, id);
        assertEquals(id, customerInDB.getId());
        assertEquals(newFirstname, customerInDB.getFirstname());
    }

    @Test
    public void refresh() throws Exception {
        String id = _customer.getId();
        assertNotNull("ID should not be null", id);
        String newFirstname = "Marcus";
        _customer.setFirstname(newFirstname);
        assertEquals(newFirstname, _customer.getFirstname());
        _em.refresh(_customer);
        assertEquals("Mark", _customer.getFirstname());
    }

    @Test
    public void remove() throws Exception {
        String id = _customer.getId();
        assertNotNull("ID should not be null", id);
        _tx.begin();
        _em.remove(_customer);
        _tx.commit();
        // try to find it from the database
        Customer customerInDB = _em.find(Customer.class, id);
        assertEquals(null, customerInDB);
    }

    @Test
    public void detach() throws Exception {
        String id = _customer.getId();
        assertNotNull("ID should not be null", id);
        assertTrue(_em.contains(_customer));
        _em.detach(_customer);
        assertFalse(_em.contains(_customer));
        // find it from the database
        Customer customerInDB = _em.find(Customer.class, id);
        assertEquals(id, customerInDB.getId());
        // set _customer managed again
        _customer = customerInDB;
    }

    @Test
    public void merge() throws Exception {
        String id = _customer.getId();
        assertNotNull("ID should not be null", id);
        assertTrue(_em.contains(_customer));
        _em.detach(_customer);
        assertFalse(_em.contains(_customer));
        String newFirstname = "Marcus";
        _customer.setFirstname(newFirstname);
        _tx.begin();
        _em.merge(_customer);
        _tx.commit();
        // find it from the database
        Customer customerInDB = _em.find(Customer.class, id);
        assertEquals(id, customerInDB.getId());
        assertEquals(newFirstname, customerInDB.getFirstname());
        // set _customer managed again
        _customer = customerInDB;
    }
}