package com.yaps.petstore.server.domain;

import junit.framework.TestCase;
import junit.framework.TestSuite;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.yaps.petstore.server.domain.customer.Customer;


/**
 * This class tests operations on persistent customers ... using JUnit 3
 */
public final class JPACustomerTest extends TestCase {
    private static String _persistenceUnitName = "petstorePU";
    private static EntityManagerFactory _emf;
    private static EntityManager _em;
    private static EntityTransaction _tx;
    private Customer _customer;

    public static junit.framework.Test suite() {
        return new TestSuite(JPACustomerTest.class);
    }

    // @BeforeClass
    public static void initEntityManager() throws Exception {
        _emf = Persistence.createEntityManagerFactory(_persistenceUnitName);
        _em = _emf.createEntityManager();
    }

    // @AfterClass
    public static void closeEntityManager() {
        _em.close();
        _emf.close();
    }

    // @Before
    public void initTransactionAndManagedCustomer() {
        _tx = _em.getTransaction();
        _customer = new Customer(null, "Mark", "Zuckerberg");
        _tx.begin();
        _em.persist(_customer);
        _tx.commit();
    }
    
    // @After
    public void removeTestedCustomer() {
    	if ( !_em.contains(_customer) )
    		return;
        _tx.begin();
        _em.remove(_customer);
        _tx.commit();
    }

    public void setUp() {
    	try {
			initEntityManager();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        initTransactionAndManagedCustomer();
    }
    
    public void tearDown() {
    	removeTestedCustomer();
    	closeEntityManager();
    }
    

    //==================================
    //=            Test cases          =
    //==================================
    // @Test
    public void testFfind() throws Exception {
        String id = _customer.getId();
        assertNotNull("ID should not be null", id);
        // find it from the database
        Customer customerInDB = _em.find(Customer.class, id);
        assertEquals(id, customerInDB.getId());
        assertEquals(_customer, customerInDB);
    }

    // @Test
    public void testUpdate() throws Exception {
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

    // @Test
    public void testRefresh() throws Exception {
        String id = _customer.getId();
        assertNotNull("ID should not be null", id);
        String newFirstname = "Marcus";
        _customer.setFirstname(newFirstname);
        assertEquals(newFirstname, _customer.getFirstname());
        _em.refresh(_customer);
        assertEquals("Mark", _customer.getFirstname());
    }

    // @Test
    public void testRemove() throws Exception {
        String id = _customer.getId();
        assertNotNull("ID should not be null", id);
        _tx.begin();
        _em.remove(_customer);
        _tx.commit();
        // try to find it from the database
        Customer customerInDB = _em.find(Customer.class, id);
        assertEquals(null, customerInDB);
    }

    // @Test
    public void testDetach() throws Exception {
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

    // @Test
    public void testMerge() throws Exception {
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