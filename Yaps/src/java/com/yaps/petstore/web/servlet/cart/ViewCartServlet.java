package com.yaps.petstore.web.servlet.cart;

import com.yaps.petstore.common.delegate.ShoppingCartDelegate;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.web.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * This servlet browses the content of the shopping cart.
 */
public class ViewCartServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        final Collection cartItemsDTO;
        final Double total;

        try {
            // Gets the content of the Shopping Cart and
        	ShoppingCartDelegate shoppingCartDelegate = new ShoppingCartDelegate(request.getSession().getId());
            cartItemsDTO = shoppingCartDelegate.getItems();

            // ... the total of the shopping cart and
            total = shoppingCartDelegate.getTotal();

            // ... puts all the data into the request
            request.setAttribute("cartItemsDTO", cartItemsDTO);
            request.setAttribute("total", total);

            // Goes to the cart page passing the request
            getServletContext().getRequestDispatcher("/cart.jsp").forward(request, response);

        } catch (RemoteException e) {
            Trace.throwing(getCname(), mname, e);
            getServletContext().getRequestDispatcher("/error.jsp?exception=Cannot view the shopping cart").forward(request, response);
        }
    }
}
