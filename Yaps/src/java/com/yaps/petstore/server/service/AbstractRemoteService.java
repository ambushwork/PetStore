package com.yaps.petstore.server.service;

import com.yaps.petstore.common.exception.CheckException;

/**
 * A service is a class that follows the Facade Design Pattern. It gives a set of services
 * to remote or local client. Every service class should extend this class.
 */
public abstract class AbstractRemoteService {

    // Used for logging
    private final transient String _cname = this.getClass().getName();

    // ======================================
    // =            Constructors            =
    // ======================================
    protected AbstractRemoteService()  {
    }

    protected void checkId(final String id) throws CheckException {
    	if ( id == null || id.equals("") )
    		throw new CheckException("Id should not be null or empty");    	
    }
    // ======================================
    // =         Getters and Setters        =
    // ======================================
    protected final String getCname() {
        return _cname;
    }
}
