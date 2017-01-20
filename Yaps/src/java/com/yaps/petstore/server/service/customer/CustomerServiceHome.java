package com.yaps.petstore.server.service.customer;

/**
 * This interface provides the JNDI name of the CustomerService EJB
 */
public interface CustomerServiceHome {
    // https://glassfish.java.net/javaee5/ejb/EJB_FAQ.html#What_is_the_syntax_for_portable_global_
    static final String JNDI_NAME = "java:global/yapswtp12/CustomerSB!com.yaps.petstore.server.service.customer.CustomerService";
}
