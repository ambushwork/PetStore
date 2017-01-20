package com.yaps.petstore.server.domain.category;

import com.yaps.petstore.server.domain.order.Order;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

public class CategoryDAO  extends AbstractDataAccessObject<String, Category>  {

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
    public CategoryDAO() {
    	this("petstorePU");
    }
    
    public CategoryDAO(String persistenceUnitName) {
    	super(persistenceUnitName);
    }
    // ======================================
    // =           Business methods         =
    // ======================================
}
