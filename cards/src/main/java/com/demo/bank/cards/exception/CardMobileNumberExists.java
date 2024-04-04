package com.demo.bank.cards.exception;

import com.demo.bank.cards.constants.ExceptionMessages;

public class CardMobileNumberExists extends RuntimeException {
    public CardMobileNumberExists(String mobileNumber) {
        super(String.format(ExceptionMessages.CARD_MOBILE_ALREADY_EXISTS, mobileNumber));
    }
}
