package com.yaps.petstore.web.servlet;

import com.yaps.petstore.common.delegate.CatalogDelegate;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.logging.Trace;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * This servlet returns the list of all products.
 */
public class FindProductsServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        final Collection productsDTO;
        String categoryId = request.getParameter("categoryId");

        try {
            // Gets the products for a category id
            Trace.finest(getCname(), mname, "Category id=" + categoryId);
            productsDTO = CatalogDelegate.findProducts(categoryId);

            // puts the list of products into the request
            request.setAttribute("productsDTO", productsDTO);

            // Goes to the products page passing the request
            getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);

        } catch (ObjectNotFoundException e) {
            getServletContext().getRequestDispatcher("/error.jsp?exception=No products found for category " + categoryId).forward(request, response);
        } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
            getServletContext().getRequestDispatcher("/error.jsp?exception=Cannot get the product list: " + e.getMessage()).forward(request, response);
        }
    }
}
