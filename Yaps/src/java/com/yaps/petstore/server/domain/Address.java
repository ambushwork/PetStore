package com.yaps.petstore.server.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.yaps.petstore.common.exception.CheckException;
import java.io.Serializable;


/**
 * This class encapsulates all the data for an address.
 *
 * @see com.yaps.petstore.server.domain.customer.Customer
 * @see com.yaps.petstore.server.domain.order.Order
 */
@Embeddable
public class Address implements Serializable  {

    // ======================================
    // =             Attributes             =
    // ======================================
    @Column(name = "street1", length = 50)
    private String _street1;
    @Column(name = "street2", length = 50)
    private String _street2;
    @Column(name = "city", length = 25)
    private String _city;
    @Column(name = "state", length = 25)
    private String _state;
    @Column(name = "zipcode", length = 10)
    private String _zipcode;
    @Column(name = "country", length = 25)
    private String _country;

    // ======================================
    // =           Business methods         =
    // ======================================
    public void checkData() throws CheckException {
        if (_city == null || "".equals(_city))
            throw new CheckException("Invalid city");
        if (_country == null || "".equals(_country))
            throw new CheckException("Invalid country");
        if (_street1 == null || "".equals(_street1))
            throw new CheckException("Invalid street");
        if (_zipcode == null || "".equals(_zipcode))
            throw new CheckException("Invalid zipcode");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getStreet1() {
        return _street1;
    }

    public void setStreet1(final String street1) {
        _street1 = street1;
    }

    public String getStreet2() {
        return _street2;
    }

    public void setStreet2(final String street2) {
        _street2 = street2;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(final String city) {
        _city = city;
    }

    public String getState() {
        return _state;
    }

    public void setState(final String state) {
        _state = state;
    }

    public String getZipcode() {
        return _zipcode;
    }

    public void setZipcode(final String zipcode) {
        _zipcode = zipcode;
    }

    public String getCountry() {
        return _country;
    }

    public void setCountry(final String country) {
        _country = country;
    }
}
