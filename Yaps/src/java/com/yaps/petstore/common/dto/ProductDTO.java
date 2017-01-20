package com.yaps.petstore.common.dto;


/**
 * This class follows the Data Transfert Object design pattern and for that implements the
 * markup interface DataTransfertObject. It is a client view of a Product. This class only
 * transfers data from a distant service to a client.
 */
public final class ProductDTO implements DataTransfertObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _id;
    private String _name;
    private String _description;
    private String _categoryId;
    private String _categoryName;

    // ======================================
    // =            Constructors            =
    // ======================================
    public ProductDTO() {
    }

    public ProductDTO(final String id, final String name, final String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getDescription() {
        return _description;
    }

    public void setDescription(final String description) {
        _description = description;
    }

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

    public String getCategoryId() {
        return _categoryId;
    }

    public void setCategoryId(final String categoryId) {
        _categoryId = categoryId;
    }

    public String getCategoryName() {
        return _categoryName;
    }

    public void setCategoryName(final String categoryName) {
        _categoryName = categoryName;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("ProductDTO{");
        buf.append("id=").append(getId());
        buf.append(",name=").append(getName());
        buf.append(",description=").append(getDescription());
        buf.append(",categoryId=").append(getCategoryId());
        buf.append(",categoryName=").append(getCategoryName());
        buf.append('}');
        return buf.toString();
    }
}
