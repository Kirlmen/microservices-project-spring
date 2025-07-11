package com.kirl.accounts.service;

import com.kirl.accounts.dto.CustomerDto;

public interface IAccountsService {

	/**
	 *
	 * @param customerDto - CustomerDto Object
	 */
	void createAccount(CustomerDto customerDto);

	/**
	 *
	 * @param mobileNumber-Input Mobile number
	 * @return account details based on a mobile number
	 */
	CustomerDto fetchAccount(String mobileNumber);

	/**
	 *
	 * @param customerDto- CustomerDto object
	 * @return boolean indicating if the update successful or not
	 */
	boolean updateAccount(CustomerDto customerDto);

	/**
	 * Deletes the account associated with the given mobile number.
	 *
	 * @param mobileNumber the mobile number linked to the account to be deleted
	 * @return true if the account was successfully deleted, false otherwise
	 */
	boolean deleteAccount(String mobileNumber);


}
