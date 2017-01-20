package com.yaps.petstore.web.servlet;

import com.yaps.petstore.common.delegate.CustomerDelegate;
import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.logging.Trace;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet creates a customer.
 */
public final class UpdateCustomerServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        CustomerDTO customerDTO = new CustomerDTO();

        // Personal information
        customerDTO.setId(request.getParameter("id"));
        customerDTO.setPassword(request.getParameter("password"));
        customerDTO.setFirstname(request.getParameter("firstname"));
        customerDTO.setLastname(request.getParameter("lastname"));
        customerDTO.setEmail(request.getParameter("email"));
        customerDTO.setTelephone(request.getParameter("telephone"));
        // Personal information
        customerDTO.setStreet1(request.getParameter("street1"));
        customerDTO.setStreet2(request.getParameter("street2"));
        customerDTO.setCity(request.getParameter("city"));
        customerDTO.setState(request.getParameter("state"));
        customerDTO.setZipcode(request.getParameter("zipcode"));
        customerDTO.setCountry(request.getParameter("country"));
        // Credit Card Information
        customerDTO.setCreditCardType(request.getParameter("creditCardType"));
        customerDTO.setCreditCardNumber(request.getParameter("creditCardNumber"));
        customerDTO.setCreditCardExpiryDate(request.getParameter("creditCardExpiryDate"));

        try {
            // Updates the customer
            CustomerDelegate.updateCustomer(customerDTO);

            // puts the customerDTO into the session
            request.getSession().setAttribute("customerDTO", customerDTO);

            // Goes to the index page passing the request
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (CheckException e) {
            getServletContext().getRequestDispatcher("/error.jsp?exception=" + e.getMessage()).forward(request, response);
        } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}