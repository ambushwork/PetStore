package com.yaps.petstore.server.domain.item;

import java.io.Serializable;

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
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.yaps.petstore.server.domain.DomainObject;
import com.yaps.petstore.server.domain.product.Product;
import com.yaps.petstore.common.exception.CheckException;

/**
 * This class represents an Item in the catalog of the YAPS company.
 * The catalog is divided into categories. Each one divided into products
 * and each product in items.
 */

@Entity
@NamedQueries( {
	@NamedQuery(name = "Item.findAll", query="select o from Item o"),
	@NamedQuery(name = "Item.findAllInProduct", query="select ol from Item ol where ol._product._id = :productId"),
	@NamedQuery(name = "Item.search", query="select p from Item p where p._id like :keyword or p._name like :keyword")
} )
@Table(name = "T_ITEM")

public final class Item extends DomainObject implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
	@Id
    @Column(name = "id", length = 10)
	@TableGenerator(name="TABLE_GEN_ITEM", table="T_COUNTER", pkColumnName="name",
    valueColumnName="value", pkColumnValue="Item")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN_ITEM") 
    // see http://en.wikibooks.org/wiki/Java_Persistence/Identity_and_Sequencing#Table_sequencing
	private String _id;
	
	@Column(name = "name",nullable = false, length = 10)
    private String _name;
	
	@Column(name = "unitCost",nullable = false)
    private double _unitCost;
	
	@Column(name = "imagePath",nullable = true,length = 255)
    private String _imagePath;
	
	@ManyToOne (fetch =FetchType.EAGER)
	@JoinColumn(name ="product_fk", nullable = false)
    private Product _product;

    // ======================================
    // =            Constructors            =
    // ======================================
    public Item() {
    }

    public Item(final String id) {
        setId(id);
    }

    public Item(final String id, final String name, final double unitCost, final Product product) {
        setId(id);
        setName(name);
        setUnitCost(unitCost);
        setProduct(product);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public void checkData() throws CheckException {
        if (getName() == null || "".equals(getName()))
            throw new CheckException("Invalid name");
        if (getUnitCost() <= 0)
            throw new CheckException("Invalid unit cost");
        if (getProduct() == null || getProduct().getId() == null || "".equals(getProduct().getId()))
            throw new CheckException("Invalid product");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getId(){
    	return this._id;
    }
    
    public void setId(String id){
    	this._id = id;
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

    public Product getProduct() {
        return _product;
    }

    public void setProduct(final Product product) {
        _product = product;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tItem {");
        buf.append("\n\t\tId=").append(getId());
        buf.append("\n\t\tName=").append(getName());
        buf.append("\n\t\tUnit Cost=").append(getUnitCost());
        buf.append("\n\t\timagePath=").append(getImagePath());
        buf.append("\n\t\tProduct Id=").append(getProduct().getId());
        buf.append("\n\t\tProduct Name=").append(getProduct().getName());
        buf.append("\n\t}");
        return buf.toString();
    }
}
