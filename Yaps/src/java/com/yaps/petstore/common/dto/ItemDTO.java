package com.yaps.petstore.common.dto;

/**
 * This class follows the Data Transfert Object design pattern and for that implements the
 * markup interface DataTransfertObject. It is a client view of an Item. This class only
 * transfers data from a distant service to a client.
 */
public final class ItemDTO implements DataTransfertObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _id;
    private String _name;
    private double _unitCost;
    private String _imagePath;
    private String _productId;
    private String _productName;
    private String _productDescription;

    // ======================================
    // =            Constructors            =
    // ======================================
    public ItemDTO() {
    }

    public ItemDTO(final String id, final String name, final double unitCost) {
        setId(id);
        setName(name);
        setUnitCost(unitCost);
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getId() {
        return _id;
    }

    public void setId(final String id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(final String name) {
        _name = name;
    }

    public double getUnitCost() {
        return _unitCost;
    }

    public void setUnitCost(final double unitCost) {
        _unitCost = unitCost;
    }

    public String getImagePath() {
        return _imagePath;
    }

    public void setImagePath(final String imagePath) {
        _imagePath = imagePath;
    }

    public String getProductId() {
        return _productId;
    }

    public void setProductId(final String productId) {
        _productId = productId;
    }

    public String getProductName() {
        return _productName;
    }

    public void setProductName(final String productName) {
        _productName = productName;
    }

    public String getProductDescription() {
        return _productDescription;
    }

    public void setProductDescription(final String productDescription) {
        _productDescription = productDescription;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("ItemDTO{");
        buf.append("id=").append(getId());
        buf.append(",name=").append(getName());
        buf.append(",unitCost=").append(getUnitCost());
        buf.append(",imagePath=").append(getImagePath());
        buf.append(",productId=").append(getProductId());
        buf.append(",productName=").append(getProductName());
        buf.append(",productDescription=").append(getProductDescription());
        buf.append('}');
        return buf.toString();
    }
}
