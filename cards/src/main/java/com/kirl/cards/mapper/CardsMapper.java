package com.kirl.cards.mapper;

import com.kirl.cards.dto.CardsDto;
import com.kirl.cards.entity.Cards;

public class CardsMapper {

	public static CardsDto mapToCardsDto(Cards cards, CardsDto cardsDto){
		cardsDto.setCardNumber(cards.getCardNumber());
		cardsDto.setCardType(cards.getCardType());
		cardsDto.setMobileNumber(cards.getMobileNumber());
		cardsDto.setAmountUsed(cards.getAmountUsed());
		cardsDto.setTotalLimit(cards.getTotalLimit());
		cardsDto.setAvailableAmount(cards.getAvailableAmount());
		return cardsDto;
	}

	public static Cards mapToCards(CardsDto cardsDto, Cards cards){
		cards.setCardNumber(cardsDto.getCardNumber());
		cards.setCardType(cardsDto.getCardType());
		cards.setMobileNumber(cardsDto.getMobileNumber());
		cards.setAmountUsed(cardsDto.getAmountUsed());
		cards.setTotalLimit(cardsDto.getTotalLimit());
		cards.setAvailableAmount(cardsDto.getAvailableAmount());
		return cards;
	}
}
