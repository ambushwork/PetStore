package com.yaps.petstore.common.delegate;

import com.yaps.petstore.common.dto.OrderDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.CreateException;
import com.yaps.petstore.common.exception.FinderException;
import com.yaps.petstore.common.exception.RemoveException;
import com.yaps.petstore.common.locator.ServiceLocator;
import com.yaps.petstore.server.service.order.OrderService;
import com.yaps.petstore.server.service.order.OrderServiceHome;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * This class follows the Delegate design pattern. It's a one to one method
 * with the OrderService class. Each method delegates the call to the
 * OrderService class
 */
public final class OrderDelegate {

    // =================================
    // =             Attributes             =
    // ======================================

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * Delegates the call to the {@link OrderService#createOrder(String, Map) OrderService().createOrder} method.
     */
    public static String createOrder(final String customerId, final Map shoppingCart) throws CreateException, CheckException, RemoteException {
        return getOrderService().createOrder(customerId, shoppingCart);
    }

    /**
     * Delegates the call to the {@link OrderService#createOrder(OrderDTO) OrderService().createOrder} method.
     */
    public static OrderDTO createOrder(final OrderDTO orderDTO) throws CreateException, CheckException, RemoteException {
        return getOrderService().createOrder(orderDTO);
    }

    /**
     * Delegates the call to the {@link OrderService#findOrder(String) OrderService().findOrder} method.
     */
    public static OrderDTO findOrder(final String orderId) throws FinderException, CheckException, RemoteException {
        return getOrderService().findOrder(orderId);
    }

    /**
     * Delegates the call to the {@link OrderService#deleteOrder(String) OrderService().deleteOrder} method.
     */
    public static void deleteOrder(final String orderId) throws RemoveException, CheckException, RemoteException {
        getOrderService().deleteOrder(orderId);
    }

    // ======================================
    // =            Private methods         =
    // ======================================
    private static OrderService getOrderService() throws RemoteException {
        OrderService orderServiceRemote = null;
        try {
            // Looks up for the home interface
            orderServiceRemote = (OrderService) ServiceLocator.getInstance().getHome(OrderServiceHome.JNDI_NAME);
        } catch (Exception e) {
            throw new RemoteException("Lookup or Create exception", e);
        }

        return orderServiceRemote;
    }
}
