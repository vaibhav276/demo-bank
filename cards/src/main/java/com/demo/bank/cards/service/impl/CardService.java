package com.demo.bank.cards.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.demo.bank.cards.constants.CardConstants;
import com.demo.bank.cards.dto.CardDto;
import com.demo.bank.cards.entity.Card;
import com.demo.bank.cards.exception.CardMobileNumberExists;
import com.demo.bank.cards.exception.ResourceNotFoundException;
import com.demo.bank.cards.mapper.CardMapper;
import com.demo.bank.cards.repository.CardsRepository;
import com.demo.bank.cards.service.ICardService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardService implements ICardService {

    CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> optionalCard = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCard.isPresent()) {
            throw new CardMobileNumberExists(mobileNumber);
        }

        Card card = createNewCard(mobileNumber);
        cardsRepository.save(card);
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardDto getByMobileNumber(String mobileNumber) {
        Card card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardMapper.mapToCardDto(card, new CardDto());
    }

    @Override
    public boolean updateCardDetails(CardDto cardDto) {
        Card card = cardsRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(
            () -> new ResourceNotFoundException("Card", "cardNumber", cardDto.getCardNumber())
        );
        CardMapper.mapToCard(cardDto, card);
        cardsRepository.save(card);

        return true;
    }

    @Override
    public boolean deleteByMobileNumber(String mobileNumber) {
        Card card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(card.getCardId());

        return true;
    }
    
}
