package com.yaps.petstore.web.servlet;

import com.yaps.petstore.common.delegate.CustomerDelegate;
import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.FinderException;
import com.yaps.petstore.common.logging.Trace;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignOnServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        final CustomerDTO customerDTO;
        String customerId = request.getParameter("customerId");
        String password = request.getParameter("password");

        try {
            // Autentificates the customer
            customerDTO = CustomerDelegate.authenticate(customerId, password);

            // We put the customerDTO into the session
            Trace.finest(getCname(), mname, "customerDTO set into http session");
            request.getSession().setAttribute("customerDTO", customerDTO);

            // Goes to the main page passing the request
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (FinderException e) {
            getServletContext().getRequestDispatcher("/error.jsp?exception=Customer Id does not exist").forward(request, response);
        } catch (CheckException e) {
            getServletContext().getRequestDispatcher("/error.jsp?exception=" + e.getMessage()).forward(request, response);
        } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
            getServletContext().getRequestDispatcher("/error.jsp?exception=" + e.getMessage()).forward(request, response);
        }
    }
}
