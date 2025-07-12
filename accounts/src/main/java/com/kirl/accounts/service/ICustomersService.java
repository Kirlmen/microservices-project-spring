package com.kirl.accounts.service;

import com.kirl.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {

	/**
	 * Fetches the customer details for the given mobile number.
	 *
	 * @param mobileNumber the mobile number of the customer
	 * @return a CustomerDetailsDto based on the given mobileNumber
	 */
	CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
