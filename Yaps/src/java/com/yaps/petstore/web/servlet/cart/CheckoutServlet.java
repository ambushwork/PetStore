package com.yaps.petstore.web.servlet.cart;

import com.yaps.petstore.common.delegate.OrderDelegate;
import com.yaps.petstore.common.delegate.ShoppingCartDelegate;
import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.web.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * This servlet checks out the shopping cart. It creates an order with the content of
 * the shopping cart and then empties it.
 */
public class CheckoutServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        final String orderId;
        final String customerId;
        final Map shoppingCart;

        try {
            // Gets the customer id and the shopping cart
            customerId = ((CustomerDTO)request.getSession().getAttribute("customerDTO")).getId();
        	ShoppingCartDelegate shoppingCartDelegate = new ShoppingCartDelegate(request.getSession().getId());
            shoppingCart = shoppingCartDelegate.getCart();

            // Creates the order and
            orderId = OrderDelegate.createOrder(customerId, shoppingCart);

            // Empties the shopping cart
            shoppingCartDelegate.empty();

            // ... puts all the order id into the request
            request.setAttribute("orderId", orderId);

            // Goes to the checkout page passing the request
            getServletContext().getRequestDispatcher("/checkout.jsp").forward(request, response);

        } catch (Exception e) {
            Trace.throwing(getCname(), mname, e);
            getServletContext().getRequestDispatcher("/error.jsp?exception=Cannot complete the order:" + e).forward(request, response);
        }
    }
}
