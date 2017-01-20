package com.barkbank.verifier;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.yaps.petstore.AbstractTestCase;
import junit.framework.TestSuite;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.net.URLEncoder;

/**
 * This class tests the VerifyCreditCard servlet
 */
public class VerifyCreditCardServletTest extends AbstractTestCase {

    private WebConversation webConversation = new WebConversation();

    public VerifyCreditCardServletTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(VerifyCreditCardServletTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    private static String VALID_CREDIT_CARD = "Valid";
    private static String INVALID_DATE = "Invalid date";
    private static String INVALID_NUMBER = "Invalid number";
    private static String INVALID_CREDIT_CARD = "Validation Exception";

    /**
     * This method sends an XML stream describing a valid credit card.
     */
    public void testBarkBankVerifyValidCreditCard() throws Exception {
        String ccXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<CreditCard>" +
                "<CardNumber>4564 1231 4564 2222</CardNumber>" +
                "<CardType>Visa</CardType>" +
                "<ExpiryDate Month=\"10\" Year=\"18\"/>" +
                "</CreditCard>";

        String status = sendToServletAndGetStatus(ccXML);

        // If the credit card is not 'Valid' an exception is thrown
        if (!VALID_CREDIT_CARD.equals(status))
            fail("Credit card is valid. It status should be " + VALID_CREDIT_CARD);
    }

    /**
     * This method sends an XML stream describing a credit card with a old date.
     */
    public void testBarkBankVerifyOldCreditCard() throws Exception {
        String ccXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<CreditCard>" +
                "<CardNumber>4564 1231 4564 2222</CardNumber>" +
                "<CardType>Visa</CardType>" +
                "<ExpiryDate Month=\"10\" Year=\"01\"/>" +
                "</CreditCard>";

        String status = sendToServletAndGetStatus(ccXML);

        // If the credit card is not 'Valid' an exception is thrown
        if (!INVALID_DATE.equals(status))
            fail("Credit card is too old. It status should be " + INVALID_DATE);
    }

    /**
     * This method sends an XML stream describing a credit card with an invalid number
     * for a visa.
     */
    public void testBarkBankVerifyInvalidNumberCreditCard() throws Exception {
        String ccXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<CreditCard>" +
                "<CardNumber>4564 1231 4564 1111</CardNumber>" +
                "<CardType>Visa</CardType>" +
                "<ExpiryDate Month=\"10\" Year=\"18\"/>" +
                "</CreditCard>";

        String status = sendToServletAndGetStatus(ccXML);

        // If the credit card is not 'Valid' an exception is thrown
        if (!INVALID_NUMBER.equals(status))
            fail("Credit card number is wrong. It status should be " + INVALID_NUMBER);
    }

    /**
     * This method sends an XML stream with invalid tags.
     */
    public void testBarkBankVerifyInvalidXMLDocument() throws Exception {
        String ccXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<CreditCard>" +
                "<WrongTag>4564 1231 4564 1111</WrongTag>" +
                "<CardType>Visa</CardType>" +
                "<ExpiryDate Month=\"10\" Year=\"18\"/>" +
                "</CreditCard>";

        String status = sendToServletAndGetStatus(ccXML);

        // The status 'Validation Exception' should be received
        if (!INVALID_CREDIT_CARD.equals(status))
            fail("Wrong XML document. It status should be " + INVALID_CREDIT_CARD);
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private String sendToServletAndGetStatus(String ccXML) throws Exception {
        final String SERVLET_PARAMETER = "param";
        final String URL_SERVLET_CREDITCARD = "http://localhost:8080/barkbank/creditcard";
        final String XPATH_VERIFIER_STATUS = "//CreditCard/@Status";

        // Sends the XML to the servlet
        WebResponse verifyCCServlet = webConversation.getResponse(URL_SERVLET_CREDITCARD + "?" +
                SERVLET_PARAMETER + "=" + URLEncoder.encode(ccXML, "UTF-8"));

        // Creates an XML document from the result of the servlet
        SAXReader reader = new SAXReader();
        Document document = reader.read(verifyCCServlet.getInputStream());

        // Returns the status of the credit card
        return document.selectSingleNode(XPATH_VERIFIER_STATUS).getText();

    }
}
