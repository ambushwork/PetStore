package com.yaps.petstore.common.constant;

/**
 * This class lists all the credit card types accepted by Yaps.
 */
public class CreditCardTypes {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String[] _all = {
        ""
        , "Visa"
        , "MasterCard"
        , "American Express"};

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public static String[] getAll() {
        return _all;
    }
}
