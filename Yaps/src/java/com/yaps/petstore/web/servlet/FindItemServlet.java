package com.yaps.petstore.web.servlet;

import com.yaps.petstore.common.delegate.CatalogDelegate;
import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.logging.Trace;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet gets all the details for an Item.
 */
public class FindItemServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        final ItemDTO itemDTO;
        String itemId = request.getParameter("itemId");

        try {
            // Gets the item
            Trace.finest(getCname(), mname, "Item id=" + itemId);
            itemDTO = CatalogDelegate.findItem(itemId);

            // puts the item into the request
            request.setAttribute("itemDTO", itemDTO);

            // Goes to the item page passing the request
            getServletContext().getRequestDispatcher("/item.jsp").forward(request, response);

        } catch (ObjectNotFoundException e) {
            getServletContext().getRequestDispatcher("/error.jsp?exception=Item " + itemId + " not found").forward(request, response);
        } catch (CheckException e) {
            getServletContext().getRequestDispatcher("/error.jsp?exception=" + e.getMessage()).forward(request, response);
        } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
            getServletContext().getRequestDispatcher("/error.jsp?exception=Cannot get the item").forward(request, response);
        }
    }
}
