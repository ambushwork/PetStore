package com.yaps.petstore.server.domain.customer;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.yaps.petstore.server.domain.DomainObject;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.Address;
import com.yaps.petstore.server.domain.CreditCard;

/**
 * This class represents a customer for the YAPS company.
 */

@Entity
@NamedQuery(name = "Customer.findAll", query="select o from Customer o")
@Table(name = "T_CUSTOMER")

public final class Customer extends DomainObject implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
	
	@Id
    @Column(name = "id", length = 10)
	@TableGenerator(name="TABLE_GEN_CUSTOMER", table="T_COUNTER", pkColumnName="name",
    valueColumnName="value", pkColumnValue="Customer")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN_CUSTOMER") 
    // see http://en.wikibooks.org/wiki/Java_Persistence/Identity_and_Sequencing#Table_sequencing
	private String _id;
	
	@Column(name = "firstname", nullable = false, length = 50)
    private String _firstname;
	
	@Column(name = "lastname", nullable = false, length = 50)
    private String _lastname;
	
	@Column(name = "password", nullable = true, length = 20)
    private String _password;
	
	@Column(name = "telephone", nullable = true, length = 10)
    private String _telephone;
	
	@Column(name = "email", nullable = true, length = 255)
    private String _email;
	
	@Embedded
    private final Address _address = new Address();
	
	@Embedded
    private final CreditCard _creditCard = new CreditCard();

    // ======================================
    // =            Constructors            =
    // ======================================
    public Customer() {
    }

    public Customer(final String id) {
        setId(id);
    }

    public Customer(final String id, final String firstname, final String lastname) {
        setId(id);
        setFirstname(firstname);
        setLastname(lastname);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method checks the integrity of the object data.
     *
     * @throws CheckException if data is invalid
     */
    public void checkData() throws CheckException {
        if (getFirstname() == null || "".equals(getFirstname()))
            throw new CheckException("Invalid customer first name");
        if (getLastname() == null || "".equals(getLastname()))
            throw new CheckException("Invalid customer last name");
    }

    /**
     * Given a password, this method then checks if it matches the user
     *
     * @param password
     * @throws CheckException thrown if the password is empty or different than the one
     *                        store in database
     */
    public void matchPassword(String password) throws CheckException {
        if (password == null || "".equals(password))
            throw new CheckException("Invalid password");

        // The password entered by the customer is not the same stored in database
        if (!password.equals(getPassword()))
            throw new CheckException("Password doesn't match");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getFirstname() {
        return _firstname;
    }

    public void setFirstname(final String firstname) {
        _firstname = firstname;
    }

    public String getLastname() {
        return _lastname;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = password;
    }

    public void setLastname(final String lastname) {
        _lastname = lastname;
    }

    public String getTelephone() {
        return _telephone;
    }

    public void setTelephone(final String telephone) {
        _telephone = telephone;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(final String email) {
        _email = email;
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

    public CreditCard getCreditCard() {
        return _creditCard;
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

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Customer{");
        buf.append("id=").append(getId());
        buf.append(",firstname=").append(getFirstname());
        buf.append(",lastname=").append(getLastname());
        buf.append(",password=").append(getPassword());
        buf.append(",telephone=").append(getTelephone());
        buf.append(",email=").append(getEmail());
        buf.append(",street1=").append(getStreet1());
        buf.append(",street2=").append(getStreet2());
        buf.append(",city=").append(getCity());
        buf.append(",state=").append(getState());
        buf.append(",zipcode=").append(getZipcode());
        buf.append(",country=").append(getCountry());
        buf.append(",creditCardNumber=").append(getCreditCardNumber());
        buf.append(",creditCardType=").append(getCreditCardType());
        buf.append(",creditCardExpiryDate=").append(getCreditCardExpiryDate());
        buf.append('}');
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
