package com.yaps.petstore.web;

import com.meterware.httpunit.WebConversation;
import com.yaps.petstore.AbstractTestCase;
import junit.framework.TestSuite;

/**
 * This class tests the HTML Pages and servlets
 */
public class WebTest extends AbstractTestCase {

    private WebConversation webConversation = new WebConversation();
    private static final String URL_PETSTORE = "http://localhost:8080/yapswtp12";
    private static final String URL_BARKBANK = "http://localhost:8080/barkbank";

    public WebTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(WebTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * Checks that all HTML and JSP pages are deployed
     */
    public void testWebCheckPages() {
        try {
            webConversation.getResponse(URL_PETSTORE + "/dummy.html");
            fail("A dummy HTML page has been found on petstore");
        } catch (Exception e) {
        }

        try {
            webConversation.getResponse(URL_PETSTORE);
        } catch (Exception e) {
            fail("Root " + URL_PETSTORE + " hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/createcustomer.html");
            fail("createcustomer.html must be replaced by createcustomer.jsp");
        } catch (Exception e) {
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/index.jsp");
        } catch (Exception e) {
            fail("index.jsp hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/error.jsp");
        } catch (Exception e) {
            fail("error.jsp hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/notfound.jsp");
        } catch (Exception e) {
            fail("notfound.jsp hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/createcustomer.jsp");
        } catch (Exception e) {
            fail("createcustomer.jsp hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/updatecustomer.jsp");
        } catch (Exception e) {
            fail("updatecustomer.jsp hasn't been found");
        }

        try {
            webConversation.getResponse(URL_BARKBANK + "/dummy.html");
            fail("A dummy HTML page has been found on barkbank");
        } catch (Exception e) {
        }

        try {
            webConversation.getResponse(URL_BARKBANK);
        } catch (Exception e) {
            fail("Root " + URL_BARKBANK + " hasn't been found");
        }
    }

    /**
     * Checks that all login pages and servlets pages are deployed
     */
    public void testWebCheckLogin() {
        try {
            webConversation.getResponse(URL_PETSTORE + "/signon.jsp");
        } catch (Exception e) {
            fail("signon.jsp hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/signon");
        } catch (Exception e) {
            fail("SignOnServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/signoff.jsp");
        } catch (Exception e) {
            fail("signoff.jsp hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/signoff");
        } catch (Exception e) {
            fail("SignOffServlet hasn't been found");
        }
    }

    /**
     * Checks that all shopping cart pages and servlets pages are deployed
     */
    public void testWebShoppingCart() {
        try {
            webConversation.getResponse(URL_PETSTORE + "/cart.jsp");
        } catch (Exception e) {
            fail("cart.jsp hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/addtocart");
        } catch (Exception e) {
            fail("AddItemToCartServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/removefromcart");
        } catch (Exception e) {
            fail("RemoveItemFromCartServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/viewcart");
        } catch (Exception e) {
            fail("ViewCartServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/checkout");
        } catch (Exception e) {
            fail("CheckoutServlet hasn't been found");
        }
    }

    /**
     * Checks that all servlets are deployed
     */
    public void testWebCheckServlets() {
        try {
            webConversation.getResponse(URL_PETSTORE + "/dummy");
            fail("A dummy servlet has been found on petstore");
        } catch (Exception e) {
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/error");
            fail("The ErrorServlet is not used anymore. You have to use the error.jsp instead.");
        } catch (Exception e) {
        }
    
        try {
            webConversation.getResponse(URL_PETSTORE + "/createcustomer");
        } catch (Exception e) {
            fail("The CreateCustomerServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/updatecustomer");
        } catch (Exception e) {
            fail("The UpdateCustomerServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/finditem");
        } catch (Exception e) {
            fail("The FindItemServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/finditems");
        } catch (Exception e) {
            fail("The FindItemsServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/findproducts");
        } catch (Exception e) {
            fail("The FindProductsServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/searchitems");
        } catch (Exception e) {
            fail("The SearchItemsServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_BARKBANK + "/dummy");
            fail("A dummy servlet has been found on barkbank");
        } catch (Exception e) {
        }

        try {
            webConversation.getResponse(URL_BARKBANK + "/creditcard");
        } catch (Exception e) {
            fail("The CreditCardServlet hasn't been found");
        }
    }
}
