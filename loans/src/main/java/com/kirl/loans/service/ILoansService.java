package com.kirl.loans.service;

import com.kirl.loans.dto.LoansDto;
import org.springframework.stereotype.Service;

public interface ILoansService {
	/**
	 * @param mobileNumber - Mobile Number of the Customer
	 */
	void createLoan(String mobileNumber);

	LoansDto fetchLoans(String mobileNumber);

	boolean updateLoans(LoansDto loansDto);

	boolean deleteLoans(String mobileNumber);

}

