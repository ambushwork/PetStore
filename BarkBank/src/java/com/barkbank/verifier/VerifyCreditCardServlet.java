package com.barkbank.verifier;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * This class represents an external service from a barkbank that validates
 * credit cards numbers. It receives an XML stream that looks like that
 * <CreditCard>
 * <CardNumber>1213 4654 1321 4562</CardNumber>
 * <CardType>Visa</CardType>
 * <ExpiryDate Month="01" Year="05"/>
 * </CreditCard>
 */
public class VerifyCreditCardServlet extends HttpServlet {

    // ======================================
    // =             Attributes             =
    // ======================================
    // Used for logging
    private final transient String cname = this.getClass().getName();
    private static final Logger logger = Logger.getLogger("com.barkbank.verifier");


    // Parameter passed to the servlet
    private static final String SERVLET_PARAMETER = "param";

    // XPath
    private static final String XPATH_CARD_NUMBER = "//CardNumber";
    private static final String XPATH_CARD_TYPE = "//CardType";
    private static final String XPATH_EXPIRY_MONTH = "//ExpiryDate/@Month";
    private static final String XPATH_EXPIRY_YEAR = "//ExpiryDate/@Year";
    // For XML
    private static final String XML_CREDITCARD_STATUS = "Status";

    private static final String INVALID_CREDIT_CARD = "Validation Exception";

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        String mname = "service";
        logger.entering(cname, mname, request.getParameter(SERVLET_PARAMETER));

        try {
            // Gets the XML stream and...
            String ccXml = request.getParameter(SERVLET_PARAMETER);
            // ... transforms it into a XML Document
            Document document = DocumentHelper.parseText(ccXml);

            // Verifies the validity of data
            String status = verifyCreditCard(document);

            // creates the XML result to send back
            Document ccXmlResult = createResult(document, status);

            // Sends back the result
            PrintWriter out;
            response.setContentType("text/xml");
            out = response.getWriter();
            out.println(ccXmlResult.asXML());
            out.close();

        } catch (Exception e) {
            logger.throwing(cname, mname, e);
        }

        logger.exiting(cname, mname);
    }

    /**
     * Verifies the credit card information by calling the CreditCardVerifier
     * and returns the status
     *
     * @param document the xml representation of a credit card
     * @return true if the card is valid, false is it's not
     */
    private String verifyCreditCard(Document document) {
        String mname = "verifyCreditCard";
        logger.entering(cname, mname, document.asXML());

        String status = null;
        try {
            // Gets the credit card data
            String ccNumber = document.selectSingleNode(XPATH_CARD_NUMBER).getText();
            String ccType = document.selectSingleNode(XPATH_CARD_TYPE).getText();
            String ccExpiryMonth = document.selectSingleNode(XPATH_EXPIRY_MONTH).getText();
            String ccExpiryYear = document.selectSingleNode(XPATH_EXPIRY_YEAR).getText();

            // Verifies the credit card
            status = VerificationAlgorithm.verify(ccNumber, ccType, ccExpiryYear, ccExpiryMonth);

        } catch (Exception e) {
            // If there's a parsing exception, the status of the card is invalid
            status = INVALID_CREDIT_CARD;
        }

        // Returns the status
        logger.exiting(cname, mname, status);
        return status;
    }

    /**
     * This method adds the status of the verifier (eg. 'Valid' or 'Invalid Number')
     * to the XML document
     *
     * @param document original document
     * @param status   of the credit card
     * @return new DOM document which is the original one plus the 'Status' attribute
     */
    private Document createResult(Document document, String status) {
        String mname = "createResult";
        logger.entering(cname, mname, new Object[]{document.asXML(), status});

        document.getRootElement().addAttribute(XML_CREDITCARD_STATUS, status);

        logger.exiting(cname, mname, document.asXML());
        return document;
    }

}
