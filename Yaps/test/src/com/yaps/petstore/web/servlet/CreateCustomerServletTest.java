package com.yaps.petstore.web.servlet;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.delegate.CustomerDelegate;
import com.yaps.petstore.common.dto.CustomerDTO;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.FinderException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.exception.RemoveException;
import java.rmi.RemoteException;

import junit.framework.TestSuite;

/**
 * This class tests the CreateCustomer servlet
 */
public class CreateCustomerServletTest extends AbstractTestCase {

    private WebConversation webConversation = new WebConversation();
    private static final String URL_PETSTORE = "http://localhost:8080/yapswtp12";

    public CreateCustomerServletTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CreateCustomerServletTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it using the web page and checks it then exists.
     */
    public void testServletCreateCustomer() throws Exception {
        final int id = getPossibleUniqueIntId();
        CustomerDTO customerDTO = null;

        // Ensures that the object doesn't exist
        try {
            customerDTO = findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customerDTO = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customerDTO, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCustomer(id);
            fail("An object with the same id has already been created");
        } catch (Exception e) {
        }

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServletCreateCustomerWithInvalidPassword() throws Exception {
        final int id = getPossibleUniqueIntId();

        // Creates an object with invalid password
        try {
            createInvalidPasswordCustomer(id);
            fail("Object with invalid password should not be created");
        } catch (Exception e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServletCreateCustomerWithInvalidValues() throws Exception {
        final int id = getPossibleUniqueIntId();

        // Creates an object with empty values
        try {
            createInvalidCustomer(id);
            fail("Object with empty values should not be created");
        } catch (Exception e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private CustomerDTO findCustomer(final int id) throws FinderException, CheckException {
        CustomerDTO customerDTO = null;

        try {
            customerDTO = CustomerDelegate.findCustomer("custo" + id);
        } catch (RemoteException e) {
            fail("Service is bound. Call should have succed");
    }

        return customerDTO;
    }

    private WebResponse signInCustomer(final int id) throws Exception {
        // Gets the Sign On Web Page
        WebResponse signOnPage = webConversation.getResponse(URL_PETSTORE + "/signon.jsp");
        WebForm newCustomerForm = signOnPage.getFormWithName("newCustomerForm");
        // Sets parameter to the web page
        newCustomerForm.setParameter("id", "custo" + id);
        newCustomerForm.setParameter("password", "password" + id);
        newCustomerForm.setParameter("password2", "password" + id);
        // Submits the form
        return newCustomerForm.submit();
    }

    private void createCustomer(final int id) throws Exception {
        // Gets the create customer page Web Page
        WebResponse customerPage = signInCustomer(id);
        // ... and get the form
        WebForm createCustomerForm = customerPage.getFormWithName("customerForm");

        // Sets parameter to the web page
        createCustomerForm.setParameter("firstname", "firstname" + id);
        createCustomerForm.setParameter("lastname", "lastname" + id);
        createCustomerForm.setParameter("email", "email" + id);
        createCustomerForm.setParameter("telephone", "phone" + id);
        createCustomerForm.setParameter("street1", "street1" + id);
        createCustomerForm.setParameter("street2", "street2" + id);
        createCustomerForm.setParameter("city", "city" + id);
        createCustomerForm.setParameter("state", "Alabama");
        createCustomerForm.setParameter("zipcode", "zip" + id);
        createCustomerForm.setParameter("country", "USA");
        createCustomerForm.setParameter("creditCardExpiryDate", "10/18");
        createCustomerForm.setParameter("creditCardNumber", "4564 1231 4564 1222");
        createCustomerForm.setParameter("creditCardType", "Visa");

        // Submits the form
        createCustomerForm.submit();
        // If after clicking the submit button an error occurs,
        // the page title is 'YAPS Error'
        WebResponse currentPage = webConversation.getCurrentPage();
        if ("YAPS Error".equals(currentPage.getTitle()))
            throw new Exception("An error has occured");
    }

    private void createInvalidCustomer(final int id) throws Exception {
        // Gets the create customer page Web Page
        WebResponse customerPage = signInCustomer(id);
        // ... and get the form
        WebForm createCustomerForm = customerPage.getFormWithName("customerForm");

        // Sets empty parameters to the web page
        createCustomerForm.setParameter("firstname", "");
        createCustomerForm.setParameter("lastname", "");
        // Submits the form
        createCustomerForm.submit();
        // If after clicking the submit button an error occurs,
        // the page title is 'YAPS Error'
        WebResponse currentPage = webConversation.getCurrentPage();
        if ("YAPS Error".equals(currentPage.getTitle()))
            throw new Exception("An error has occured");
    }

    private void createInvalidPasswordCustomer(int id) throws Exception {
        // Gets the Sign On Web Page
        WebResponse signOnPage = webConversation.getResponse(URL_PETSTORE + "/signon.jsp");
        WebForm newCustomerForm = signOnPage.getFormWithName("newCustomerForm");
        // Sets parameter to the web page with 2 different passwords
        newCustomerForm.setParameter("id", "custo" + id);
        newCustomerForm.setParameter("password", "password" + id);
        newCustomerForm.setParameter("password2", "differentpassword" + id);
        // Submits the form
        newCustomerForm.submit();
        // If after clicking the submit button an error occurs,
        // the page title is 'YAPS Error'
        WebResponse currentPage = webConversation.getCurrentPage();
        if ("YAPS Error".equals(currentPage.getTitle()))
            throw new Exception("An error has occured");
    }

    private void removeCustomer(final int id) throws RemoveException, CheckException, RemoteException {
        CustomerDelegate.deleteCustomer("custo" + id);
    }

    private void checkCustomer(final CustomerDTO customerDTO, final int id) {
        assertEquals("firstname", "firstname" + id, customerDTO.getFirstname());
        assertEquals("lastname", "lastname" + id, customerDTO.getLastname());
        assertEquals("city", "city" + id, customerDTO.getCity());
        assertEquals("country", "USA", customerDTO.getCountry());
        assertEquals("state", "Alabama", customerDTO.getState());
        assertEquals("street1", "street1" + id, customerDTO.getStreet1());
        assertEquals("street2", "street2" + id, customerDTO.getStreet2());
        assertEquals("telephone", "phone" + id, customerDTO.getTelephone());
        assertEquals("email", "email" + id, customerDTO.getEmail());
        assertEquals("zipcode", "zip" + id, customerDTO.getZipcode());
        assertEquals("CreditCardExpiryDate", "10/18", customerDTO.getCreditCardExpiryDate());
        assertEquals("CreditCardNumber", "4564 1231 4564 1222", customerDTO.getCreditCardNumber());
        assertEquals("CreditCardType", "Visa", customerDTO.getCreditCardType());
    }
}
