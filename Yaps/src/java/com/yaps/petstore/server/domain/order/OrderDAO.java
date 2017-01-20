package com.yaps.petstore.server.domain.order;

import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

/**
 * This class does all the database access for the class Order.
 *
 * @see Order
 */
public final class OrderDAO extends AbstractDataAccessObject<String, Order>  {

    // ======================================
    // =             Attributes             =
    // ======================================
    // Used to get a unique id with the UniqueIdGenerator
    private static final String COUNTER_NAME = "Order";
	protected String getCounterName() {
		return COUNTER_NAME;
	}

    // ======================================
    // =            Constructors            =
    // ======================================
    public OrderDAO() {
    	this("petstorePU");
    }
    
    public OrderDAO(String persistenceUnitName) {
    	super(persistenceUnitName);
    }
    // ======================================
    // =           Business methods         =
    // ======================================

}
