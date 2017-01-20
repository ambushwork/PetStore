package com.yaps.petstore.server.service.creditcard;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.logging.Trace;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * This class sends a HTTP request to a servlet.
 */
final class HTTPSender {

    // ======================================
    // =             Attributes             =
    // ======================================
    // Used for logging
    private final static String _cname = HTTPSender.class.getName();

    private static final String SERVLET_PARAMETER = "param";
    private static final String URL_SERVLET_CREDITCARD = "http://localhost:8080/barkbank/creditcard";

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method takes data as a parameter and send it to a servlet. The response
     * of the servlet is returnes back.
     *
     * @param creditCardXML String representation of data to send.
     * @return the response of the remote servlet after execution
     * @throws CheckException thrown if there's a comunication or parsing problem
     */
    public static Document send(final Document creditCardXML) throws CheckException {
        final String mname = "send";
        Trace.entering(_cname, mname);

        Document creditCardVerifiedXML ;

        try {
            // Encodes the data to send
            final String xmlEncoded = URLEncoder.encode(creditCardXML.asXML(), "UTF-8");

            // Create the URL with name of the servlet and the data as a parameter
            final URL url = new URL(URL_SERVLET_CREDITCARD + "?" + SERVLET_PARAMETER + "=" + xmlEncoded);
            Trace.finest(_cname, mname, "URL=" + url);

            // Opens the connection with the servlet
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            // Gets the result from the servlet
            final InputStream is = conn.getInputStream();

            // Creates an XML document from the InputStream
            final SAXReader reader = new SAXReader();
            creditCardVerifiedXML = reader.read(is);

        } catch (Exception e) {
            throw new CheckException("Invalid Card");
        }

        Trace.exiting(_cname, mname);
        return creditCardVerifiedXML;
    }
}
