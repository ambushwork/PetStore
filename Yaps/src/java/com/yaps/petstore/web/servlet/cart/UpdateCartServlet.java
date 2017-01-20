package com.yaps.petstore.web.servlet.cart;

import com.yaps.petstore.common.delegate.ShoppingCartDelegate;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.web.servlet.AbstractServlet;

import java.io.IOException;

import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateCartServlet  extends AbstractServlet {
    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        try {
            // Updates the itemId with quantity into the Shopping Cart
        	ShoppingCartDelegate shoppingCartDelegate = new ShoppingCartDelegate(request.getSession().getId());      	
            shoppingCartDelegate.updateItemQuantity(request.getParameter("itemId"), 
                                                    Integer.parseInt(request.getParameter("quantity")));

            getServletContext().getRequestDispatcher("/viewcart").forward(request, response);

        } catch (RemoteException e) {
            Trace.throwing(getCname(), mname, e);
            getServletContext().getRequestDispatcher("/error.jsp?exception=Cannot remove the item from the shopping cart").forward(request, response);
        }
    }
}
