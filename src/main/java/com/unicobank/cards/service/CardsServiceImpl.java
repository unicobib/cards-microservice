package com.unicobank.cards.service;

import com.unicobank.cards.constants.CardsConstants;
import com.unicobank.cards.dto.CardsDto;
import com.unicobank.cards.entity.Cards;
import com.unicobank.cards.exception.CardAlreadyExistsException;
import com.unicobank.cards.exception.ResourceNotFoundException;
import com.unicobank.cards.mapper.CardsMapper;
import com.unicobank.cards.repository.CardsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService{

    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        var cardsOptional = cardsRepository.findByMobileNumber(mobileNumber);
        cardsOptional.ifPresentOrElse(cards -> {
            throw new CardAlreadyExistsException("A card already registered with the given mobileNumber "+mobileNumber);
        },
                () -> cardsRepository.save(createNewCard(mobileNumber))
        );
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        var cardsOptional = cardsRepository.findByMobileNumber(mobileNumber);

        return cardsOptional.map(cards -> CardsMapper.mapToCardsDto(cards, new CardsDto()))
                .orElseThrow(() -> new ResourceNotFoundException("Cards", "mobileNumber", mobileNumber));
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        var cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "cardNumber", cardsDto.getCardNumber())
        );
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        var cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }
}
