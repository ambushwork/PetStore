package com.yaps.petstore.common.dto;

import java.util.Collection;
import java.util.Date;

/**
 * This class follows the Data Transfert Object design pattern and for that implements the
 * markup interface DataTransfertObject. It is a client view of an Order. This class only
 * transfers data from a distant service to a client.
 */
public final class OrderDTO implements DataTransfertObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _id;
    private Date _orderDate;
    private String _firstname;
    private String _lastname;
    private final AddressDTO _address = new AddressDTO();
    private final CreditCardDTO _creditCard = new CreditCardDTO();
    private Collection _orderLines;
    private String _customerId;

    // ======================================
    // =            Constructors            =
    // ======================================
    public OrderDTO() {
    }

    public OrderDTO(final String firstname, final String lastname, final String street1, final String city, final String zipcode, final String country) {
        setFirstname(firstname);
        setLastname(lastname);
        setStreet1(street1);
        setCity(city);
        setZipcode(zipcode);
        setCountry(country);
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

    public Date getOrderDate() {
        return _orderDate;
    }

    public void setOrderDate(final Date orderDate) {
        _orderDate = orderDate;
    }

    public String getFirstname() {
        return _firstname;
    }

    public void setFirstname(final String firstname) {
        _firstname = firstname;
    }

    public String getLastname() {
        return _lastname;
    }

    public void setLastname(final String lastname) {
        _lastname = lastname;
    }

    public String getStreet1() {
        return _address.getStreet1();
    }

    public void setStreet1(final String street1) {
        _address.setStreet1(street1);
    }

    public String getStreet2() {
        return _address.getStreet2();
    }

    public void setStreet2(final String street2) {
        _address.setStreet2(street2);
    }

    public String getCity() {
        return _address.getCity();
    }

    public void setCity(final String city) {
        _address.setCity(city);
    }

    public String getState() {
        return _address.getState();
    }

    public void setState(final String state) {
        _address.setState(state);
    }

    public String getZipcode() {
        return _address.getZipcode();
    }

    public void setZipcode(final String zipcode) {
        _address.setZipcode(zipcode);
    }

    public String getCountry() {
        return _address.getCountry();
    }

    public void setCountry(final String country) {
        _address.setCountry(country);
    }

    public String getCreditCardNumber() {
        return _creditCard.getCreditCardNumber();
    }

    public void setCreditCardNumber(final String creditCardNumber) {
        _creditCard.setCreditCardNumber(creditCardNumber);
    }

    public String getCreditCardType() {
        return _creditCard.getCreditCardType();
    }

    public void setCreditCardType(final String creditCardType) {
        _creditCard.setCreditCardType(creditCardType);
    }

    public String getCreditCardExpiryDate() {
        return _creditCard.getCreditCardExpiryDate();
    }

    public void setCreditCardExpiryDate(final String creditCardExpiryDate) {
        _creditCard.setCreditCardExpiryDate(creditCardExpiryDate);
    }

    public Collection getOrderLines() {
        return _orderLines;
    }

    public void setOrderLines(final Collection orderLines) {
        _orderLines = orderLines;
    }

    public String getCustomerId() {
        return _customerId;
    }

    public void setCustomerId(final String customerId) {
        _customerId = customerId;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("OrderDTO{");
        buf.append("id=").append(getId());
        buf.append(",orderDate=").append(getOrderDate());
        buf.append(",firstname=").append(getFirstname());
        buf.append(",lastname=").append(getLastname());
        buf.append(",street1=").append(getStreet1());
        buf.append(",street2=").append(getStreet2());
        buf.append(",city=").append(getCity());
        buf.append(",state=").append(getState());
        buf.append(",zipcode=").append(getZipcode());
        buf.append(",country=").append(getCountry());
        buf.append(",creditCardNumber=").append(getCreditCardNumber());
        buf.append(",creditCardType=").append(getCreditCardType());
        buf.append(",creditCardExpiry Date=").append(getCreditCardExpiryDate());
        buf.append(",customerId=").append(getCustomerId());
        buf.append(",[orderLines=").append(getOrderLines()).append(']');
        buf.append('}');
        return buf.toString();
    }
}
