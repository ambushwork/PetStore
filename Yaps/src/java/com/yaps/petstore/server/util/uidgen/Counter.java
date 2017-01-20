package com.yaps.petstore.server.util.uidgen;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yaps.petstore.server.domain.DomainObject;

@Entity
@Table(name = "T_COUNTER")
public class Counter extends DomainObject implements Serializable{
    // ======================================
    // =             Attributes             =
    // ======================================
    @Id
    @Column(name = "name", nullable = false, length = 50)
    private String id;
    @Column(name = "value")
    private int nextId;

    // ======================================
    // =            Constructors            =
    // ======================================
    public Counter() {
    }

    public Counter(final String id, final int nextId) {
        setId(id);
        setNextId(nextId);
    }

	// ======================================
    // =         Getters and Setters        =
    // ======================================
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}
}
