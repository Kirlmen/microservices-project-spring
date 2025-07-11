package com.kirl.loans.service.impl;

import com.kirl.loans.constants.LoansConstants;
import com.kirl.loans.dto.LoansDto;
import com.kirl.loans.entity.Loans;
import com.kirl.loans.exception.LoanAlreadyExistsException;
import com.kirl.loans.exception.ResourceNotFoundException;
import com.kirl.loans.mapper.LoansMapper;
import com.kirl.loans.repository.LoansRepository;
import com.kirl.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class ILoansServiceImpl implements ILoansService {

	LoansRepository loansRepository;

	/**
	 * @param mobileNumber - Mobile Number of the Customer
	 */
	@Override
	public void createLoan(String mobileNumber) {
		Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
		if(optionalLoans.isPresent()){
			throw new LoanAlreadyExistsException("Loan already exists on given mobile number: " + mobileNumber);
		}
		loansRepository.save(createNewLoan(mobileNumber));
	}

	/**
	 * Fetches the loan details associated with the specified mobile number.
	 *
	 * @param mobileNumber the mobile number of the customer whose loan details are to be retrieved
	 * @return a LoansDto containing the loan details including mobile number, loan number, loan type, total loan amount, amount paid, and outstanding amount
	 */
	@Override
	public LoansDto fetchLoans(String mobileNumber) {
		Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
		);
		return LoansMapper.mapToLoansDto(loans, new LoansDto());
	}

	/**
	 *
	 * @param loansDto the DTO containing updated loan details, including loan ID, mobile number,
	 *                 loan type, total loan amount, amount paid, and outstanding amount
	 * @return true if the update operation is successful; false otherwise
	 */
	@Override
	public boolean updateLoans(LoansDto loansDto) {
		Loans storedLoan = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
				() -> new ResourceNotFoundException("Loan", "loanNumber", loansDto.getLoanNumber())
		);
		LoansMapper.mapToLoans(loansDto, storedLoan);
		loansRepository.save(storedLoan);
		return true;
	}

	@Override
	public boolean deleteLoans(String mobileNumber) {
		Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
		);
		loansRepository.delete(loans);
		return true;
	}

	/**
	 * @param mobileNumber - Mobile Number of the Customer
	 * @return the new loan details
	 */
	private Loans createNewLoan(String mobileNumber){
		Loans newLoans = new Loans();
		long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
		newLoans.setLoanNumber(Long.toString(randomLoanNumber));
		newLoans.setMobileNumber(mobileNumber);
		newLoans.setLoanType(LoansConstants.HOME_LOAN);
		newLoans.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
		newLoans.setAmountPaid(0);
		newLoans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
		return newLoans;
	}
}
