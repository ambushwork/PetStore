package com.yaps.petstore.web.servlet;

import com.yaps.petstore.common.logging.Trace;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet creates a customer.
 */
public final class CreateNewAccountServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        // Personal information
        String customerId = request.getParameter("id");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        // Id and password must be filled
        if ("".equals(customerId) || "".equals(password) || "".equals(password2)) {
            getServletContext().getRequestDispatcher("/error.jsp?exception=Id and passwords have to be filled").forward(request, response);

        } else if (!password.equals(password2)) {
            // Both entered passwords are different
            getServletContext().getRequestDispatcher("/error.jsp?exception=Both passwords are different").forward(request, response);

        } else {
            // puts the customerId and password into the request
            request.setAttribute("id", customerId);
            request.setAttribute("password", password);

            // Goes to the create customer page passing the request
            getServletContext().getRequestDispatcher("/createcustomer.jsp").forward(request, response);
        }
    }

}