package com.kirl.loans.mapper;

import com.kirl.loans.dto.LoansDto;
import com.kirl.loans.entity.Loans;

public class LoansMapper {


	public static LoansDto mapToLoansDto(Loans loans, LoansDto loansDto){
		loansDto.setMobileNumber(loans.getMobileNumber());
		loansDto.setLoanType(loans.getLoanType());
		loansDto.setLoanNumber(loans.getLoanNumber());
		loansDto.setTotalLoan(loans.getTotalLoan());
		loansDto.setAmountPaid(loans.getAmountPaid());
		loansDto.setOutstandingAmount(loans.getOutstandingAmount());
		return loansDto;
	}

	public static Loans mapToLoans(LoansDto loansDto, Loans loans){
		loans.setMobileNumber(loansDto.getMobileNumber());
		loans.setLoanType(loansDto.getLoanType());
		loans.setLoanNumber(loansDto.getLoanNumber());
		loans.setTotalLoan(loansDto.getTotalLoan());
		loans.setAmountPaid(loansDto.getAmountPaid());
		loans.setOutstandingAmount(loansDto.getOutstandingAmount());
		return loans;


	}
}
