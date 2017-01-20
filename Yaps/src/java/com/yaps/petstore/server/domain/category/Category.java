package com.yaps.petstore.server.domain.category;

import com.yaps.petstore.server.domain.DomainObject;
import com.yaps.petstore.server.domain.product.Product;
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * This class represents a Category in the catalog of the YAPS company.
 * The catalog is divided into categories. Each one divided into products
 * and each product in items.
 */
@Entity
@NamedQuery(name = "Category.findAll", query="select o from Category o")
@Table(name = "T_CATEGORY")

public final class Category extends DomainObject implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
	@Id
    @Column(name = "id", length = 10)
	@TableGenerator(name="TABLE_GEN_CATEGORY", table="T_COUNTER", pkColumnName="name",
    valueColumnName="value", pkColumnValue="Category")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN_CATEGORY") 
    // see http://en.wikibooks.org/wiki/Java_Persistence/Identity_and_Sequencing#Table_sequencing
	private String _id;
	
	@Column(name = "name",nullable = false, length = 10)
    private String _name;
	
	@Column(name = "description",nullable = false, length = 255)
    private String _description;
	
	@OneToMany (mappedBy ="_category", fetch =FetchType.EAGER, cascade =CascadeType.ALL)
    private Collection<Product> _products;

    // ======================================
    // =            Constructors            =
    // ======================================
    public Category() {
    }

    public Category(final String id) {
        setId(id);
    }

    public Category(final String id, final String name, final String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    // ======================================
    // =           Business methods         =
    // ======================================

    public void checkData() throws CheckException {
        if (getName() == null || "".equals(getName()))
            throw new CheckException("Invalid name");
        if (getDescription() == null || "".equals(getDescription()))
            throw new CheckException("Invalid description");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    
	@Override
	public String getId() {
		return this._id;
	}

	@Override
	public void setId(String id) {
		this._id = id;
		
	}
    
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

    public Collection getProducts() {
        return _products;
    }
    
    private void setProducts(final Collection products) {
        _products = products;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tCategory {");
        buf.append("\n\t\tId=").append(getId());
        buf.append("\n\t\tName=").append(getName());
        buf.append("\n\t\tDescription=").append(getDescription());
        buf.append("\n\t}");
        return buf.toString();
    }


}
