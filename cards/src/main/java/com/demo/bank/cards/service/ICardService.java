package com.demo.bank.cards.service;

import com.demo.bank.cards.dto.CardDto;

public interface ICardService {
    /**
     * @param mobileNumber
     */
    void createCard(String mobileNUmber);

    /**
     * @param mobileNumber
     * @return CardDto
     */
    CardDto getByMobileNumber(String mobileNumber);

    /**
     * @param cardDto
     * @return boolean - indicating if the operation succeeded
     */
    boolean updateCardDetails(CardDto cardDto);

    /**
     * @param mobileNumber
     * @return boolean - indicating if the operation succeeded
     */
    boolean deleteByMobileNumber(String mobileNumber);
}
