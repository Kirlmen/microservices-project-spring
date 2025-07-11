package com.kirl.cards.service;

import com.kirl.cards.dto.CardsDto;

public interface ICardsService {


	/**
	 *
	 * @param mobileNumber- Mobile number of the Customer
	 */
	void createCards(String mobileNumber);

	/**
	 *
	 * @param mobileNumber- Mobile number of the Customer
	 * @return Details of the Cards belong to Customer
	 */
	CardsDto fetchCards(String mobileNumber);

	/**
	 * Updates the details of a customer's card.
	 *
	 * @param cardsDto the details of the card to be updated, including mobile number, card number,
	 *                 card type, and financial limits.
	 * @return true if the update is successful, false otherwise.
	 */
	boolean updateCards(CardsDto cardsDto);

	/**
	 *
	 * @param mobileNumber-Mobile number of the customer
	 * @return boolean, if it's true, deletion is successful, or otherwise.
	 */
	boolean deleteCards(String mobileNumber);
}
