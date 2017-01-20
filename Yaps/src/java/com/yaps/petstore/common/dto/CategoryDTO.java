package com.yaps.petstore.common.dto;

/**
 * This class follows the Data Transfert Object design pattern and for that implements the
 * markup interface DataTransfertObject. It is a client view of a Category. This class only
 * transfers data from a distant service to a client.
 */
public final class CategoryDTO implements DataTransfertObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _id;
    private String _name;
    private String _description;

    // ======================================
    // =            Constructors            =
    // ======================================
    public CategoryDTO() {
    }

    public CategoryDTO(final String id, final String name, final String description) {
        _id = id;
        _name = name;
        _description = description;
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

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("CategoryDTO{");
        buf.append("id=").append(getId());
        buf.append(",name=").append(getName());
        buf.append(",description=").append(getDescription());
        buf.append('}');
        return buf.toString();
    }
}
