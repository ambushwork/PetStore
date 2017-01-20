package com.yaps.petstore.web.servlet.cart;

import com.yaps.petstore.common.delegate.ShoppingCartDelegate;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.web.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * This servlet adds and item to the shopping cart.
 */
public class AddItemToCartServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        try {
            // Adds the itemId into the Shopping Cart
        	ShoppingCartDelegate delegate = new ShoppingCartDelegate(request.getSession().getId());
            delegate.addItem(request.getParameter("itemId"));

            getServletContext().getRequestDispatcher("/viewcart").forward(request, response);

        } catch (RemoteException e) {
            Trace.throwing(getCname(), mname, e);
            getServletContext().getRequestDispatcher("/error.jsp?exception=Cannot add an item to the shopping cart").forward(request, response);
        }
    }
}
