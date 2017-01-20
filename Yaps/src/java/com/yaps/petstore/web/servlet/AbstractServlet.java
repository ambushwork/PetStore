package com.yaps.petstore.web.servlet;

import javax.servlet.http.HttpServlet;

/**
 * Every servlet should extend this class
 */
public abstract class AbstractServlet extends HttpServlet {

    // ======================================
    // =             Attributes             =
    // ======================================

    // Used for logging
    private final transient String _cname = this.getClass().getName();

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    protected String getCname() {
        return _cname;
    }
}
