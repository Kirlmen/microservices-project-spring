package com.kirl.cards.service.impl;

import com.kirl.cards.constants.CardsConstants;
import com.kirl.cards.dto.CardsDto;
import com.kirl.cards.entity.Cards;
import com.kirl.cards.exception.CardAlreadyExistsException;
import com.kirl.cards.exception.ResourceNotFoundException;
import com.kirl.cards.mapper.CardsMapper;
import com.kirl.cards.repository.CardsRepository;
import com.kirl.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class ICardsServiceImpl implements ICardsService {

	private CardsRepository cardsRepository;

	/**
	 * @param mobileNumber - Mobile Number of the Customer
	 */
	@Override
	public void createCards(String mobileNumber) {
		Optional<Cards> optCards = cardsRepository.findByMobileNumber(mobileNumber);
		if (optCards.isPresent()) {
			throw new CardAlreadyExistsException("There is a already registered card with given Mobile Number" + mobileNumber);
		}
		cardsRepository.save(createNewCard(mobileNumber));
	}

	/**
	 * @param mobileNumber - Mobile Number of the Customer
	 * @return the new card details
	 */
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

	/**
	 *
	 * @param mobileNumber- Mobile number of the Customer
	 * @return CardsDto of the given mobileNumber
	 */
	@Override
	public CardsDto fetchCards(String mobileNumber) {
		Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
				()-> new ResourceNotFoundException("cardsDto", "mobileNumber", mobileNumber)
		);

		return CardsMapper.mapToCardsDto(cards, new CardsDto());
	}

	@Override
	public boolean updateCards(CardsDto cardsDto) {
		Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
				()-> new ResourceNotFoundException("cards", "cardNumber",cardsDto.getCardNumber())
		);
		CardsMapper.mapToCards(cardsDto, cards);
		cardsRepository.save(cards);
		return true;
	}

	@Override
	public boolean deleteCards(String mobileNumber) {
		Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("cards", "mobileNumber", mobileNumber)
		);
		cardsRepository.delete(cards);
		return true;
	}

}
