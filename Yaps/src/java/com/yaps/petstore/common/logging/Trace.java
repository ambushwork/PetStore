package com.yaps.petstore.common.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class encapsulate all the logging calls. It uses the java.common.logging API to log
 * messages and exception.
 */
public final class Trace {

    private Trace() {
    }

    private static final Logger logger = Logger.getLogger("com.yaps.petstore");

    /**
     * Log a method entry.
     */
    public static void entering(final String cname, final String mname) {
        logger.entering(cname, mname);
    }

    /**
     * Log a method entry, with one parameter.
     */
    public static void entering(final String cname, final String mname, final Object param1) {
        logger.entering(cname, mname, param1);
    }

    /**
     * Log a method entry, with an array of parameters.
     */
    public static void entering(final String cname, final String mname, final Object[] params) {
        logger.entering(cname, mname, params);
    }

    /**
     * Log a method return.
     */
    public static void exiting(final String cname, final String mname) {
        logger.exiting(cname, mname);
    }

    /**
     * Log a method return, with result object.
     */
    public static void exiting(final String cname, final String mname, final Object result) {
        logger.exiting(cname, mname, result);
    }

    /**
     * Log throwing an exception.
     */
    public static void throwing(final String cname, final String mname, final Throwable thrown) {
        logger.throwing(cname, mname, thrown);
    }

    /**
     * SEVERE is a message level indicating a serious failure.
     */
    public static void severe(final String cname, final String mname, final String msg) {
        logger.logp(Level.SEVERE, cname, mname, msg);
    }

    /**
     * WARNING is a message level indicating a potential problem.
     */
    public static void warning(final String cname, final String mname, final String msg) {
        logger.logp(Level.WARNING, cname, mname, msg);
    }

    /**
     * INFO is a message level for informational messages.
     */
    public static void info(final String cname, final String mname, final String msg) {
        logger.logp(Level.INFO, cname, mname, msg);
    }

    /**
     * CONFIG is a message level for static configuration messages.
     */
    public static void config(final String cname, final String mname, final String msg) {
        logger.logp(Level.CONFIG, cname, mname, msg);
    }

    /**
     * FINE is a message level providing tracing information.
     */
    public static void fine(final String cname, final String mname, final String msg) {
        logger.logp(Level.FINE, cname, mname, msg);
    }

    /**
     * FINER indicates a fairly detailed tracing message.
     */
    public static void finer(final String cname, final String mname, final String msg) {
        logger.logp(Level.FINER, cname, mname, msg);
    }

    /**
     * FINEST indicates a highly detailed tracing message
     *
     * @param msg
     */
    public static void finest(final String cname, final String mname, final String msg) {
        logger.logp(Level.FINEST, cname, mname, msg);
    }
}
