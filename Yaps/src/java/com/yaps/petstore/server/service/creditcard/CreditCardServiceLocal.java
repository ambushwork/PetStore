package com.yaps.petstore.server.service.creditcard;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.CreditCard;

import javax.ejb.Local;

/**
 * This interface gives a local view of the CreditCardBean. Any local client that wants to call
 * a method on the CreditCardBean has to use this interface.
 */
@Local
public interface CreditCardServiceLocal {

    // ======================================
    // =           Business methods         =
    // ======================================
    void verifyCreditCard(CreditCard creditCard) throws CheckException;
}
