package com.yaps.petstore.server.util.uidgen;

import com.yaps.petstore.common.exception.DuplicateKeyException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.logging.Trace;

/**
 * This class manages unique IDs for Database.
 * It follows the Singleton design pattern.
 */
public final class UniqueIdGenerator {

    // ======================================
    // =             Attributes             =
    // ======================================
    // A handle to the unique Singleton instance.
    private static UniqueIdGenerator _instance = null;

    private final UniqueIdGeneratorDAO _dao = new UniqueIdGeneratorDAO();

    // Used for logging
    private final String _cname = this.getClass().getName();

    // ======================================
    // =            Constructors            =
    // ======================================
    /**
     * The constructor is made private to prevent others from instantiating this class.
     */
    private UniqueIdGenerator() {
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method is used by other classes to get a unique instance of the UniqueIdGenerator
     *
     * @return The unique instance of this class.
     */
    public static UniqueIdGenerator getInstance() {
        if (null == _instance) {
            _instance = new UniqueIdGenerator();
        }
        return _instance;
    }

    /**
     * This method returns a unique id for a given name. For example if you call this
     * method like this : getUniqueId("order") it will return the unique id for an order
     *
     * @param name name of the entity that we want to have a unique id
     * @return the unique id
     */
    public String getUniqueId(final String name) {
        final String mname = "getUniqueId";
        Trace.entering(_cname, mname, name);

        int nextId = 0;

        try {

            // Gets the current value
        	Counter counter = (Counter) _dao.select(name);
        	nextId = counter.getNextId() + 1;
            // Updates the value with the new id
            _dao.update(name, nextId);

        } catch (ObjectNotFoundException e) {

            // The value doesn't exist, we have to create it and we return the number 1
            try {
                _dao.insert(name);
                nextId = 1;
            } catch (DuplicateKeyException e1) {
                // Duplicate key exception cannot append
            }
        }

        Trace.exiting(_cname, mname, new Integer(nextId));
        return String.valueOf(nextId);
    }
}
