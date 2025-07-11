package com.kirl.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class LoansDto {

	@Pattern(regexp = "(^$/[0-9]{10})")
	@NotEmpty(message = "mobile number field cannot be null or empty")
	@Schema(
			description = "mobile number of the customer", example = "4855555555"
	)
    private String mobileNumber;

	@Pattern(regexp="(^$|[0-9]{12})",message = "LoanNumber must be 12 digits")
	@NotEmpty(message = "loan number field cannot be null or empty")
	@Schema(
			description = "Loan Number of the customer", example = "548732457654"
	)
	private String loanNumber;

	@NotEmpty(message = "loan type field cannot be null or empty")
	@Schema(
			description = "Type of the loan chosen at the bank", example = "Mortgage"
	)
	private String loanType;

	@Positive(message = "Total loan should be greater than zero")
	@Schema(
			description = "Total loan that customer going to pay", example = "104000"
	)
	private int totalLoan;

	@PositiveOrZero(message = "Total loan should be greater than or equal to zero")
	@Schema(
			description = "Total amount paid against a loan", example = "5000"
	)
	private int amountPaid;

	@PositiveOrZero(message = "Outstanding loan should be greater than or equal to zero")
	@Schema(
			description = "Total outstanding amount against a loan", example = "99000"
	)
	private int outstandingAmount;
}
