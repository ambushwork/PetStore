package com.yaps.petstore.server.domain.orderline;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

public final class OrderLineDAO extends AbstractDataAccessObject<String, OrderLine>  {

    // ======================================
    // =             Attributes             =
    // ======================================
    // Used to get a unique id with the UniqueIdGenerator
    private static final String COUNTER_NAME = "OrderLine";
	protected String getCounterName() {
		return COUNTER_NAME;
	}

    // ======================================
    // =            Constructors            =
    // ======================================
    public OrderLineDAO() {
    	this("petstorePU");
    }
    
    public OrderLineDAO(String persistenceUnitName) {
    	super(persistenceUnitName);
    }
    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method return all the order line from the database for a given order id.
     *
     * @param orderId
     * @return collection of OrderLine
     * @throws ObjectNotFoundException is thrown if the collection is empty
     */
	public Collection<OrderLine> findAllInOrder(String orderId) throws ObjectNotFoundException {
    	Query query = _em.createNamedQuery("OrderLine.findAllInOrder");
    	query.setParameter("orderId", orderId);
    	List<OrderLine> entities = query.getResultList();
        if (entities.isEmpty())
            throw new ObjectNotFoundException();
		return entities;
	}


}
