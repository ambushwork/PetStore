package com.yaps.petstore.server.service.catalog;

/**
 * This interface provides the JNDI name of the CatalogService EJB
 */
public interface CatalogServiceHome {
    // https://glassfish.java.net/javaee5/ejb/EJB_FAQ.html#What_is_the_syntax_for_portable_global_
    static final String JNDI_NAME = "java:global/yapswtp12/CatalogSB";
}
