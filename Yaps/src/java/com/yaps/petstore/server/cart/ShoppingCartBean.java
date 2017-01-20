package com.yaps.petstore.server.cart;

import com.yaps.petstore.server.service.AbstractRemoteService;

import com.yaps.petstore.common.dto.ShoppingCartItemDTO;

import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.server.domain.item.ItemDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;

// @Stateful (name="ShoppingCartSB", mappedName=ShoppingCartHome.JNDI_NAME)
@Stateful (name="ShoppingCartSB")
public class ShoppingCartBean extends AbstractRemoteService implements ShoppingCart {
    // ======================================
    // =             Attributes             =
    // ======================================
    private Map _shoppingCart;

    private static final ItemDAO _itemDAO = new ItemDAO();
    // ======================================
    // =            Constructors            =
    // ======================================
    public ShoppingCartBean() {
    }

    // ======================================
    // =     Lifecycle Callback methods     =
    // ======================================

    @PostConstruct
    public void initialize() {
        _shoppingCart = new HashMap();
    }

    @PreDestroy
    public void clear() {
        _shoppingCart = null;
    }

    public Map getCart() {
        return _shoppingCart;
    }

    public Collection getItems() {
        final Collection items = new ArrayList();

        Iterator it = _shoppingCart.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry keyValue = (Map.Entry)it.next();
            String itemId = (String)keyValue.getKey();
            int quantity = (Integer)keyValue.getValue();
            final Item item;
            try {
                item = (Item)_itemDAO.findByPrimaryKey(itemId);
                ShoppingCartItemDTO shoppingCartItemDTO = new ShoppingCartItemDTO(itemId, item.getName(),
                    item.getProduct().getDescription(), quantity, item.getUnitCost());
                items.add(shoppingCartItemDTO);
            } catch (ObjectNotFoundException e) {
                // TODO
            	System.out.println("ShoppingCartBean.getItems() " + e + ": " + itemId);
            }
        }
        return items;
    }

    public void addItem(String itemId) {
        _shoppingCart.put(itemId, 1);
    }

    public void removeItem(String itemId) {
        _shoppingCart.remove(itemId);
    }

    public void updateItemQuantity(String itemId, int newQty) {
        removeItem(itemId);
        if (newQty > 0) {
            _shoppingCart.put(itemId, newQty);
        }
    }

    public Double getTotal() {
        double total = 0.0;
        Collection cartItems = getItems();
        Iterator it = cartItems.iterator();
        while(it.hasNext()) {
            ShoppingCartItemDTO shoppingCartItemDTO = (ShoppingCartItemDTO)it.next();
            total += shoppingCartItemDTO.getTotalCost();
        }
        return total;
    }

    public void empty() {
        _shoppingCart.clear();
    }
}
