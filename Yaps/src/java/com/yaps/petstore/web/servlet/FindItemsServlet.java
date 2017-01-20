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
 * This servlet returns the list of all items.
 */
public class FindItemsServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        final Collection itemsDTO;
        String productId = request.getParameter("productId");

        try {
            // Gets the items for a product id
            Trace.finest(getCname(), mname, "Product id=" + productId);
            itemsDTO = CatalogDelegate.findItems(productId);

            // puts the list of items into the request
            request.setAttribute("itemsDTO", itemsDTO);

            // Goes to the items page passing the request
            getServletContext().getRequestDispatcher("/items.jsp").forward(request, response);

        } catch (ObjectNotFoundException e) {
            getServletContext().getRequestDispatcher("/error.jsp?exception=No items found for product " + productId).forward(request, response);
        } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
            getServletContext().getRequestDispatcher("/error.jsp?exception=Cannot get the item list").forward(request, response);
        }
    }
}
