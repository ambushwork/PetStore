package com.yaps.petstore.common.dto;

/**
 * This class encapsulates all the data for a credit card.
 *
 * @see CustomerDTO
 * @see OrderDTO
 */
public final class CreditCardDTO implements DataTransfertObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _creditCardNumber;
    private String _creditCardType;
    private String _creditCardExpiryDate;

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
}
