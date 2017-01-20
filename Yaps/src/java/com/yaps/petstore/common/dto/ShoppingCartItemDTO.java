package com.yaps.petstore.common.dto;

/**
 * This class follows the Data Transfert Object design pattern and for that implements the
 * markup interface DataTransfertObject. It is a client view of an item of the Shopping Cart.
 * A shopping cart is made of several items.
 * This class only transfers data from a distant service to a client.
 */
public class ShoppingCartItemDTO implements DataTransfertObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _itemId;
    private String _itemName;
    private String _productDescription;
    private int _quantity;
    private double _unitCost;

    // ======================================
    // =            Constructors            =
    // ======================================
    public ShoppingCartItemDTO(final String itemId, final String itemName, final String productDescription, final int quantity, final double unitCost) {
        _itemId = itemId;
        _itemName = itemName;
        _productDescription = productDescription;
        _quantity = quantity;
        _unitCost = unitCost;
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getItemId() {
        return _itemId;
    }

    public String getItemName() {
        return _itemName;
    }

    public String getProductDescription() {
        return _productDescription;
    }

    public int getQuantity() {
        return _quantity;
    }

    public double getUnitCost() {
        return _unitCost;
    }

    public double getTotalCost() {
        return _quantity * _unitCost;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("ItemDTO{");
        buf.append("itemId=").append(getItemId());
        buf.append(",itemName=").append(getItemName());
        buf.append(",productDescription=").append(getProductDescription());
        buf.append(",quantity=").append(getQuantity());
        buf.append(",unitCost=").append(getUnitCost());
        buf.append('}');
        return buf.toString();
    }
}
