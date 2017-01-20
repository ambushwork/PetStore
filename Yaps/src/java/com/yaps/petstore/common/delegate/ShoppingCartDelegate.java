package com.yaps.petstore.common.delegate;

import com.yaps.petstore.common.locator.ServiceLocator;
import com.yaps.petstore.server.cart.ShoppingCart;

import com.yaps.petstore.server.cart.ShoppingCartHome;
import java.rmi.RemoteException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class ShoppingCartDelegate {
    // ======================================
    // =             Attributes             =
    // ======================================
    /**(sessionId, shoppingCart) associations */
    private static HashMap<String, ShoppingCart> _shoppingCarts = new HashMap<String, ShoppingCart>();
    
    /** sessionId of the session which has created this delegate */
    private String _sessionId;

    // ======================================
    // =            Constructors            =
    // ======================================
    public ShoppingCartDelegate(String sessionId)
    {
       this._sessionId = sessionId;
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public Map getCart() throws RemoteException {
        return getShoppingCart().getCart();     
    }
    
	public String getSessionId() {
		return _sessionId;
	}

	public Collection getItems() throws RemoteException {
        return getShoppingCart().getItems();
    }
    public void addItem(String itemId) throws RemoteException {
        getShoppingCart().addItem(itemId);
    }
    
    public void removeItem(String itemId) throws RemoteException {
        getShoppingCart().removeItem(itemId);
    }
    
    public void updateItemQuantity(String itemId, int newQty) throws RemoteException {
        getShoppingCart().updateItemQuantity(itemId, newQty);
    }
    
    public Double getTotal() throws RemoteException {
        return getShoppingCart().getTotal();
    }
    
    public void empty() throws RemoteException {
        getShoppingCart().empty();
    }
    
    // ======================================
    // =            Private methods         =
    // ======================================
    private ShoppingCart getShoppingCart() throws RemoteException {
        if (_shoppingCarts.get(_sessionId) == null)
        {
          ShoppingCart shoppingCart = (ShoppingCart)ServiceLocator.getInstance().getHome(ShoppingCartHome.JNDI_NAME, false);
          _shoppingCarts.put(_sessionId, shoppingCart);
        }
        ShoppingCart result = _shoppingCarts.get(_sessionId);
        return result;
    }

}
