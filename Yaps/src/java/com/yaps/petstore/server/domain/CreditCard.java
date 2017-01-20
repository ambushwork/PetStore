package com.yaps.petstore.server.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.yaps.petstore.common.logging.Trace;


/**
 * This class encapsulates all the data for a credit card.
 *
 * @see com.yaps.petstore.server.domain.customer.Customer
 * @see com.yaps.petstore.server.domain.order.Order
 */

@Embeddable
public final class CreditCard implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
	@Column(name = "creditCardNumber", length = 25)
    private String _creditCardNumber;
	@Column(name = "creditCardType", length = 25)
    private String _creditCardType;
	@Column(name = "creditCardExpiryDate", length = 10)
    private String _creditCardExpiryDate;

    // For XML
    private static final String XML_CREDITCARD = "CreditCard";
    private static final String XML_CARD_NUMBER = "CardNumber";
    private static final String XML_CARD_TYPE = "CardType";
    private static final String XML_CARD_EXPIRY_DATE = "ExpiryDate";
    private static final String XML_EXPIRY_MONTH = "Month";
    private static final String XML_EXPIRY_YEAR = "Year";

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * Returns the XML representation of a credit card. It looks like that
     * <CreditCard>
     * <CardNumber>1213 4654 1321 4562</CardNumber>
     * <CardType>Visa</CardType>
     * <ExpiryDate Month="01" Year="05"/>
     * </CreditCard>
     *
     * @return the XML Document representaing the credit card
     */
    public Document toXML() {
        String mname = "toXML";
        //Trace.entering(getCname(), mname);

        // Creating a new DOM document
        Document document = DocumentHelper.createDocument();

        // Creates the Root element
        Element root = document.addElement(XML_CREDITCARD);

        // Adds the element CardNumber
        root.addElement(XML_CARD_NUMBER).addText(getCreditCardNumber());

        // Adds the element CardType
        root.addElement(XML_CARD_TYPE).addText(getCreditCardType());

        // Creates the element CardExpiryDate
        root.addElement(XML_CARD_EXPIRY_DATE).addAttribute(XML_EXPIRY_MONTH, getExpiryMonth()).addAttribute(XML_EXPIRY_YEAR, getExpiryYear());

        //Trace.exiting(getCname(), mname);
        return document;
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getCreditCardNumber() {
        return _creditCardNumber;
    }

    public void setCreditCardNumber(final String creditCardNumber) {
        _creditCardNumber = creditCardNumber;
    }

    public String getCreditCardType() {
        return _creditCardType;
    }

    public void setCreditCardType(final String creditCardType) {
        _creditCardType = creditCardType;
    }

    public String getCreditCardExpiryDate() {
        return _creditCardExpiryDate;
    }

    public void setCreditCardExpiryDate(final String creditCardExpiryDate) {
        _creditCardExpiryDate = creditCardExpiryDate;
    }

    private String getExpiryMonth() {
        String dateString = getCreditCardExpiryDate();
        if (dateString != null) {
            // get the slash and return the text before it
            int slashStart = dateString.indexOf("/");
            if (slashStart != -1) return dateString.substring(0, slashStart);
        }
        return "01";
    }

    private String getExpiryYear() {
        String dateString = getCreditCardExpiryDate();
        if (dateString != null) {
            // get the slash and return the text after it
            int slashStart = dateString.indexOf("/");
            if (slashStart != -1) return dateString.substring(slashStart + 1, dateString.length());

        }
        return "50";
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("CreditCard{");
        buf.append("creditCardNumber=").append(getCreditCardNumber());
        buf.append(",creditCardType=").append(getCreditCardType());
        buf.append(",creditCardExpiryDate=").append(getCreditCardExpiryDate());
        buf.append('}');
        return buf.toString();
    }


}
