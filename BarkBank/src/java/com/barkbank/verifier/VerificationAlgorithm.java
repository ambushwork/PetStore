package com.barkbank.verifier;

import java.util.Calendar;

/**
 * This class represents the BarkBank algorithm to check credit card validity.
 */
public final class VerificationAlgorithm {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final Calendar calendar = Calendar.getInstance();

    private static final String VALID_CREDIT_CARD = "Valid";
    private static final String INVALID_CREDIT_CARD = "Validation Exception";
    private static final String INVALID_DATE = "Invalid date";
    private static final String INVALID_NUMBER = "Invalid number";

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method verifies if a credit card is valid or not.
     * It first checks if the date is valid, meaning that the expiry date must be in the
     * future, not in the past. It then checks if for a Visa card its number is even.
     * If there's a number format exception, the status is invalid.
     *
     * @param ccNumber      credit card number (eg. '4520 4555 0123 4564')
     * @param ccType        credit card type (eg. 'Visa' or 'Master Card')
     * @param ccExpiryYear  expiry year formatted with two figures (eg. '04' for 2004)
     * @param ccExpiryMonth expiry month (eg. '05' for May)
     * @return a status of validity (eg. 'Valid' or 'Too old'...)
     */
    static String verify(String ccNumber, String ccType, String ccExpiryYear, String ccExpiryMonth) {

        String status = VALID_CREDIT_CARD;

        try {
            // The year is represented with only two figures, we add 2000 (eg. '05' -> '2005')
            int year = Integer.parseInt(ccExpiryYear) + 2000;
            int month = Integer.parseInt(ccExpiryMonth);
            int lastNumber = Integer.parseInt(ccNumber.substring(ccNumber.length() - 1, ccNumber.length()));

            // The year of the credit is passed
            if (year < calendar.get(Calendar.YEAR))
                return INVALID_DATE;

            // The year of the credit is the actual year but the month is older
            if (year == calendar.get(Calendar.YEAR) && month < calendar.get(Calendar.MONTH))
                return INVALID_DATE;

            // For a Visa card, its number has to be even
            if ("visa".equalsIgnoreCase(ccType) && lastNumber % 2 != 0)
                return INVALID_NUMBER;
        } catch (Exception e) {
            status = INVALID_CREDIT_CARD;
        }
        return status;
    }
}
