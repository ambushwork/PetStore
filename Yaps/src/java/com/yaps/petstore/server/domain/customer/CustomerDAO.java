package com.yaps.petstore.server.domain.customer;

import com.yaps.petstore.server.domain.order.Order;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

public class CustomerDAO extends AbstractDataAccessObject<String, Customer>{

	  // ======================================
    // =             Attributes             =
    // ======================================
    // Used to get a unique id with the UniqueIdGenerator
    private static final String COUNTER_NAME = "Customer";
	protected String getCounterName() {
		return COUNTER_NAME;
	}

    // ======================================
    // =            Constructors            =
    // ======================================
    public CustomerDAO() {
    	this("petstorePU");
    }
    
    public CustomerDAO(String persistenceUnitName) {
    	super(persistenceUnitName);
    }
    // ======================================
    // =           Business methods         =
    // ======================================
}
