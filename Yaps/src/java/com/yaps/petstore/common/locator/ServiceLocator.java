package com.yaps.petstore.common.locator;

import com.yaps.petstore.common.logging.Trace;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * This class gives POJOs, Servlets or JSPs methods to look for resources (like home, remote interfaces...).
 * It follows the singleton pattern
 */
public class ServiceLocator {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static String cname = ServiceLocator.class.getName();
    private InitialContext ic;
    //used to hold references to EJBHomes/JMS Resources for re-use
    private Map cache = Collections.synchronizedMap(new HashMap());
    // Singleton pattern
    private static ServiceLocator instance = null;

    public static final int TOMEE = 1;
    public static final int JBOSS = 2;
    public static final int GLASSFISH = 3;
    public static final int JBOSS_REMOTE = 12;

    public static ServiceLocator getInstance(int serverCode) {
    	if ( instance == null )
    		instance = new ServiceLocator(serverCode);
        return instance;
    }

    public static ServiceLocator getInstance() {
        return getInstance(JBOSS);
    }

    // ======================================
    // =            Constructors            =
    // ======================================
    private ServiceLocator(int serverCode) throws ServiceLocatorException {
        Hashtable<String, String> properties = getServerNamingProperties(serverCode);
        try {
            ic = new InitialContext(properties);
            if ( serverCode == GLASSFISH )
            	// https://glassfish.java.net/javaee5/ejb/EJB_FAQ.html#StandaloneRemoteEJB
            	ic = new InitialContext(); // call empty constructor OK for Glassfish!
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    private Hashtable<String, String> getServerNamingProperties(int serverCode) {
    	Hashtable<String, String> result = null;
    	switch ( serverCode ) {
    	case TOMEE : 
    		result = getTomeeNamingProperties();
    		break;
    	case JBOSS : 
        	result = getJBossNamingProperties();
        	break;
    	case JBOSS_REMOTE : 
        	result = getJBossRemoteNamingProperties();
        	break;
    	case GLASSFISH : 
        	result = getGlassfishNamingProperties();
        	break;
    	}
    	return result;
    }

    private Hashtable<String, String> getTomeeNamingProperties() {
        Hashtable<String, String> properties = new Hashtable<String, String>();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.RemoteInitialContextFactory");
        // properties.put(Context.PROVIDER_URL, "http://localhost:8080/openejb/ejb");
        properties.put(Context.PROVIDER_URL, "http://localhost:8080/tomee/ejb");
        properties.put("java.naming.security.principal", "tomee");
        properties.put("java.naming.security.credentials", "tomee");
        // OK for Tomee cf http://tomee.apache.org/clients.html    	
        return properties;
    }
    
    private Hashtable<String, String> getJBossNamingProperties() {
        Hashtable<String, String> properties = new Hashtable<String, String>();
        // properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        // properties.put("java.naming.provider.url", "remote://localhost:4447");
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming"); // OK and Mandatory for JBoss7 + remove jndi.properties!!?
        return properties;
    }

    private Hashtable<String, String> getJBossRemoteNamingProperties() {
        Hashtable<String, String> properties = new Hashtable<String, String>();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        properties.put("java.naming.provider.url", "remote://localhost:4447");
        // more needed ?       java.util.ServiceConfigurationError: javax.security.sasl.SaslClientFactory: Provider org.jboss.sasl.localuser.LocalUserClientFactory not found
        return properties;
    }

    private Hashtable<String, String> getGlassfishNamingProperties() {
        Hashtable<String, String> properties = new Hashtable<String, String>();
        // Not OK for Glassfish : http://docs.oracle.com/cd/E18930_01/html/821-2418/beans.html
        // properties.put("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory"); 
        // Not OK for Glassfish http://www.developpez.net/forums/d446460/java/serveurs-conteneurs-java-ee/glassfish/quels-sont-parametres-jndi-pour-glassfish/
        properties.put("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory"); 
        properties.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.put("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
    	
        return properties;
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * will get the ejb Local home factory. If this ejb home factory has already been
     * clients need to cast to the type of EJBHome they desire
     *
     * @return the EJB Home corresponding to the homeName
     */
    public Object getLocalHome(String jndiHomeName) throws ServiceLocatorException {
        String methodName = "getLocalHome";
        Trace.entering(cname, methodName, jndiHomeName);

        Object home = cache.get(jndiHomeName);
        if (home == null) {
            try {
                home = ic.lookup(jndiHomeName);
                cache.put(jndiHomeName, home);
            } catch (Exception e) {
                throw new ServiceLocatorException(e);
            }
        }
        return home;
    }

    /**
     * will get the ejb Remote home factory. If this ejb home factory has already been
     * clients need to cast to the type of EJBHome they desire
     * @param jndiHomeName
     * @param useCache     
     * @return the EJB Home corresponding to the homeName
     */
    public Object getHome(String jndiHomeName, boolean useCache) throws ServiceLocatorException {
        String methodName = "getHome";
        Trace.entering(cname, methodName, jndiHomeName);

        Object home = null;
        if ( useCache )
            home = (Object) cache.get(jndiHomeName);
        if (home == null) {
            try {
                home = ic.lookup(jndiHomeName);
                if ( useCache )
                    cache.put(jndiHomeName, home);
            } catch (Exception e) {
                throw new ServiceLocatorException(e);
            }
        }
        return home;
    }

    /**
     * 
     * @param jndiHomeName
     * @param useCache
     * @return
     * @throws ServiceLocatorException 
     */
    public Object getHome(String jndiHomeName) throws ServiceLocatorException {
        return getHome(jndiHomeName, false);
    }


    /**
     * @return the factory for the factory to get queue connections from
     */
    public QueueConnectionFactory getQueueConnectionFactory(String qConnFactoryName)
            throws ServiceLocatorException {
        String methodName = "QueueConnectionFactory";
        Trace.entering(cname, methodName, qConnFactoryName);

        QueueConnectionFactory factory = (QueueConnectionFactory) cache.get(qConnFactoryName);
        if (factory == null) {
            try {
                factory = (QueueConnectionFactory) ic.lookup(qConnFactoryName);
                cache.put(qConnFactoryName, factory);
            } catch (Exception e) {
                throw new ServiceLocatorException(e);
            }
        }
        return factory;
    }

    /**
     * @return the Queue Destination to send messages to
     */
    public Queue getQueue(String queueName) throws ServiceLocatorException {
        String methodName = "getQueue";
        Trace.entering(cname, methodName, queueName);

        Queue queue = (Queue) cache.get(queueName);
        if (queue == null) {
            try {
                queue = (Queue) ic.lookup(queueName);
                cache.put(queueName, queue);
            } catch (Exception e) {
                throw new ServiceLocatorException(e);
            }
        }
        return queue;
    }

    /**
     * This method helps in obtaining the topic factory
     *
     * @return the factory for the factory to get topic connections from
     */
    public TopicConnectionFactory getTopicConnectionFactory(String topicConnFactoryName) throws ServiceLocatorException {
        TopicConnectionFactory factory = (TopicConnectionFactory) cache.get(topicConnFactoryName);
        if (factory == null) {
            try {
                factory = (TopicConnectionFactory) ic.lookup(topicConnFactoryName);
                cache.put(topicConnFactoryName, factory);
            } catch (Exception e) {
                throw new ServiceLocatorException(e);
            }
        }
        return factory;
    }

    /**
     * This method obtains the topic itself for a caller
     *
     * @return the Topic Destination to send messages to
     */
    public Topic getTopic(String topicName) throws ServiceLocatorException {
        String methodName = "getTopic";
        Trace.entering(cname, methodName, topicName);

        Topic topic = (Topic) cache.get(topicName);
        if (topic == null) {
            try {
                topic = (Topic) ic.lookup(topicName);
                cache.put(topicName, topic);
            } catch (Exception e) {
                throw new ServiceLocatorException(e);
            }
        }
        return topic;
    }

    /**
     * This method obtains the datasource itself for a caller
     *
     * @return the DataSource corresponding to the name parameter
     */
    public DataSource getDataSource(String dataSourceName) throws ServiceLocatorException {
        String methodName = "getDataSource";
        Trace.entering(cname, methodName, dataSourceName);

        DataSource dataSource = (DataSource) cache.get(dataSourceName);
        if (dataSource == null) {
            try {
                dataSource = (DataSource) ic.lookup(dataSourceName);
                cache.put(dataSourceName, dataSource);
            } catch (Exception e) {
                throw new ServiceLocatorException(e);
            }
        }
        return dataSource;
    }

    /**
     * This method obtains the UserTransaction itself for a caller
     *
     * @return the UserTransaction corresponding to the name parameter
     */
    public UserTransaction getUserTransaction(String utName) throws ServiceLocatorException {
        String methodName = "getLocalHome";
        Trace.entering(cname, methodName, utName);

        try {
            return (UserTransaction) ic.lookup(utName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }


    /**
     * @return the URL value corresponding
     *         to the env entry name.
     */
    public URL getUrl(String envName) throws ServiceLocatorException {
        try {
            return (URL) ic.lookup(envName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * @return the boolean value corresponding
     *         to the env entry such as SEND_CONFIRMATION_MAIL property.
     */
    public boolean getBoolean(String envName) throws ServiceLocatorException {
        try {
            return ((Boolean) ic.lookup(envName)).booleanValue();
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * @return the String value corresponding
     *         to the env entry name.
     */
    public String getString(String envName) throws ServiceLocatorException {
        try {
            return (String) ic.lookup(envName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

}
