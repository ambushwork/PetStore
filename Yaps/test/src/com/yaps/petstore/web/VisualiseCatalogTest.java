package com.yaps.petstore.web;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.yaps.petstore.AbstractTestCase;
import junit.framework.TestSuite;

/**
 * This class tests the HTML Pages and servlets
 */
public class VisualiseCatalogTest extends AbstractTestCase {

    private WebConversation webConversation = new WebConversation();
    private static final String URL_PETSTORE = "http://localhost:8080/yapswtp12";

    public VisualiseCatalogTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(VisualiseCatalogTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test starts at index.jsp page and click on the products, items and item links
     */
    public void testWebVisualiseCatalog() throws Exception {

        // The test uses 4 JSP Pages
        WebResponse indexJSP, productsJSP, itemsJSP;
        WebLink productsLink, itemsLink, itemLink;

        // The test starts at the index.jsp page
        indexJSP = webConversation.getResponse(URL_PETSTORE + "/index.jsp");

        // We click on the first link of the index.jsp page
        productsLink = getTheFirstWebLinkThatMatches(indexJSP, "findproducts?categoryId");
        productsJSP = productsLink.click();

        // We click on the first link of the products.jsp
        itemsLink = getTheFirstWebLinkThatMatches(productsJSP, "finditems?productId");
        itemsJSP = itemsLink.click();

        // We click on the first link of the items.jsp
        itemLink = getTheFirstWebLinkThatMatches(itemsJSP, "finditem?itemId");
        itemLink.click();
    }

    private WebLink getTheFirstWebLinkThatMatches(WebResponse jspPage, String textToMatch) throws Exception {
        // Gets all the links that the page has
        WebLink[] jspLinks = jspPage.getLinks();

        // Looks for links that match the text we look for
        for (int i = 0; i < jspLinks.length; i++) {
            String url = jspLinks[i].getURLString();
            if (url.indexOf(textToMatch) != -1)
                return jspLinks[i];
        }

        return null;
    }
}