package com.yaps.petstore.server.domain.orderline;

import com.yaps.petstore.server.domain.DomainObject;
import com.yaps.petstore.server.domain.customer.Customer;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.server.domain.order.Order;
import com.yaps.petstore.common.exception.CheckException;

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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
/**
 * An Order has several order lines. This class represent one order line.
 */
@Entity
@NamedQueries( {
	@NamedQuery(name = "OrderLine.findAll", query="select o from OrderLine o"),
	@NamedQuery(name = "OrderLine.findAllInOrder", query="select ol from OrderLine ol where ol._order._id = :orderId")
} )
@Table(name = "T_ORDER_LINE")
public class OrderLine extends DomainObject implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
	@Id
    @Column(name = "id", length = 10)
    @TableGenerator(name="TABLE_GEN_ORDERLINE", table="T_COUNTER", pkColumnName="name",
        valueColumnName="value", pkColumnValue="OrderLine")
    @GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN_ORDERLINE") 
    private String _id;
	
	@Column(name = "quantity", nullable = false)
    private int _quantity;
	@Column(name = "unitCost", nullable = false)
    private double _unitCost;
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="item_fk", nullable = false)
    private Item _item;
	@ManyToOne (fetch =FetchType.EAGER)
	@JoinColumn(name ="order_fk", nullable = false)
    private Order _order;

    // ======================================
    // =            Constructors            =
    // ======================================
    public OrderLine() {
    }

    public OrderLine(final String id) {
        setId(id);
    }

    public OrderLine(final String id, final int quantity, final double unitCost, final Order order, final Item item) {
        setId(id);
        setQuantity(quantity);
        setUnitCost(unitCost);
        setOrder(order);
        setItem(item);
    }

    public OrderLine(final int quantity, final double unitCost, final Order order, final Item item) {
        setQuantity(quantity);
        setUnitCost(unitCost);
        setOrder(order);
        setItem(item);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    @PrePersist
    @PreUpdate
    public void checkData() throws CheckException {
        if (getUnitCost() <= 0)
            throw new CheckException("Invalid unit cost");
        if (getQuantity() <= 0)
            throw new CheckException("Invalid quantity");
        if (getOrder() == null)
            throw new CheckException("Invalid order");
        if (getItem() == null)
            throw new CheckException("Invalid item");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
	public String getId() {
		return _id;
	}

	public void setId(String id) {
		_id = id;
	}

    public int getQuantity() {
        return _quantity;
    }

    public void setQuantity(final int quantity) {
        _quantity = quantity;
    }

    public double getUnitCost() {
        return _unitCost;
    }

    public void setUnitCost(final double unitCost) {
        _unitCost = unitCost;
    }

    public Order getOrder() {
        return _order;
    }

    public void setOrder(final Order order) {
        _order = order;
    }

    public Item getItem() {
        return _item;
    }

    public void setItem(final Item item) {
        _item = item;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\nOrderLine {");
        buf.append("\n\tId=").append(getId());
        buf.append("\n\tQuantity=").append(getQuantity());
        buf.append("\n\tUnit Cost=").append(getUnitCost());
        buf.append("\n\tItem Id=").append(getItem().getId());
        buf.append("\n\tItem Name=").append(getItem().getName());
        buf.append("\n}");
        return buf.toString();
    }
}
