package com.yaps.petstore.server.domain.product;

import com.yaps.petstore.server.domain.DomainObject;
import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.common.exception.CheckException;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * This class represents a Product in the catalog of the YAPS company.
 * The catalog is divided into categories. Each one divided into products
 * and each product in items.
 */

@Entity
@NamedQueries( {
@NamedQuery(name = "Product.findAll", query="select o from Product o"),
@NamedQuery(name = "Product.findAllInCategory", query="select ol from Product ol where ol._category._id = :categoryId")
} )
@Table(name = "T_PRODUCT")
public final class Product extends DomainObject implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
	@Id
    @Column(name = "id", length = 10)
	@TableGenerator(name="TABLE_GEN_PRODUCT", table="T_COUNTER", pkColumnName="name",
    valueColumnName="value", pkColumnValue="Product")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN_PRODUCT") 
	private String _id;
	
	@Column(name = "name", nullable = false, length = 50)
    private String _name;
	
	@Column(name = "description", nullable = false, length = 255)
    private String _description;
	
	@ManyToOne (fetch =FetchType.EAGER)
	@JoinColumn(name ="category_fk", nullable = false)
    private Category _category;
	
	@OneToMany (mappedBy ="_product", fetch =FetchType.EAGER, cascade =CascadeType.ALL)
    private Collection<Item> _items;

    // ======================================
    // =            Constructors            =
    // ======================================
    public Product() {
    }

    public Product(final String id) {
        setId(id);
    }

    public Product(final String id, final String name, final String description, final Category category) {
        setId(id);
        setName(name);
        setDescription(description);
        setCategory(category);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public void checkData() throws CheckException {
        if (getName() == null || "".equals(getName()))
            throw new CheckException("Invalid name");
        if (getDescription() == null || "".equals(getDescription()))
            throw new CheckException("Invalid description");
        if (getCategory() == null || getCategory().getId() == null || "".equals(getCategory().getId()))
            throw new CheckException("Invalid category");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getName() {
        return _name;
    }

    public void setName(final String name) {
        _name = name;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(final String description) {
        _description = description;
    }

    public Category getCategory() {
        return _category;
    }

    public void setCategory(final Category category) {
        _category = category;
    }

    public Collection getItems() {
        return _items;
    }

    private void setItems(final Collection items) {
        _items = items;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tProduct {");
        buf.append("\n\t\tId=").append(getId());
        buf.append("\n\t\tName=").append(getName());
        buf.append("\n\t\tDescription=").append(getDescription());
        buf.append("\n\t\tCategory Id=").append(getCategory().getId());
        buf.append("\n\t\tCategory Name=").append(getCategory().getName());
        buf.append("\n\t}");
        return buf.toString();
    }

	@Override
	public String getId() {
		return this._id;
	}

	@Override
	public void setId(String id) {
		this._id = id;
		
	}

}
