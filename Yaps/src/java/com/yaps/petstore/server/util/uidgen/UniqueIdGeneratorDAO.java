package com.yaps.petstore.server.util.uidgen;

import com.yaps.petstore.common.exception.DataAccessException;
import com.yaps.petstore.common.exception.DuplicateKeyException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

/**
 * This class does all the database access for the class UniqueIdGenerator.
 *
 * @see UniqueIdGenerator
 */
public final class UniqueIdGeneratorDAO extends AbstractDataAccessObject<String, Counter> {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_COUNTER";

    // ======================================
    // =            Constructors            =
    // ======================================
    public UniqueIdGeneratorDAO() {
    	this("petstorePU");
    }
    
    public UniqueIdGeneratorDAO(String persistenceUnitName) {
    	super(persistenceUnitName);
    }

    /**
     * This method is used when a unique id doesn't exist. This method inserts
     * the value '1' into the database. Meaning, 1 is the first identifier.
     *
     * @param name name of the counter
     * @throws DuplicateKeyException is thrown when an identical object is already in the persistent layer
     */
    public void insert(final String name) throws DuplicateKeyException {
        try {
        	findById(name);
        	throw new DuplicateKeyException();
        }
        catch ( ObjectNotFoundException e) {        	
        }
        Counter counter = new Counter(name, 1);
        persist(counter);
    }

    /**
     * This method deletes the counter name from the database.
     *
     * @param name of the counter to be deleted
     * @throws ObjectNotFoundException is thrown if the object id not found in the persistent layer
     */
    public void remove(final String name) throws ObjectNotFoundException {
        Counter counter = findById(name);
        remove(counter);
    }

    /**
     * This method updates the value of the counter in the database.
     *
     * @param name  of the counter to be updated in the database
     * @param value new value to update
     * @throws ObjectNotFoundException is thrown if the object id not found in the database
     * @throws DataAccessException     is thrown if there's a persistent problem
     */
    public void update(final String name, final int value) throws ObjectNotFoundException {
        Counter counter = findById(name);
        counter.setNextId(value);
        merge(counter);
    }

    protected String getCounterName() {
		return "";
	}
}
