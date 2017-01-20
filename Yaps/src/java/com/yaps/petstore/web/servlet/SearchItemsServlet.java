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
 * This servlet returns the list of searched items.
 */
public class SearchItemsServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        final Collection itemsDTO;
        String keyword = request.getParameter("keyword");

        try {
            // Search the items
            itemsDTO = CatalogDelegate.searchItems(keyword);

            // puts the list of items into the request and the keyword
            request.setAttribute("itemsDTO", itemsDTO);
            request.setAttribute("keyword", keyword);

            // Goes to the items page passing the request
            getServletContext().getRequestDispatcher("/items.jsp").forward(request, response);

        } catch (ObjectNotFoundException e) {
            getServletContext().getRequestDispatcher("/error.jsp?exception=No items found for keyword " + keyword).forward(request, response);
        } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
            getServletContext().getRequestDispatcher("/error.jsp?exception=Cannot search for items").forward(request, response);
        }
    }
}
