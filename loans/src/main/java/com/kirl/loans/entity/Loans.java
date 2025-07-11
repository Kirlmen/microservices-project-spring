package com.kirl.loans.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class Loans extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_id")
	private Long LoanId;

	@Column(name = "mobile_number")
	@NotNull
	private String mobileNumber;

	@Column(name = "loan_number")
	@NotNull
	private String loanNumber;

	@Column(name = "loan_type")
	@NotNull
	private String loanType;

	@Column(name = "total_loan")
	@NotNull
	private int totalLoan;

	@Column(name = "amount_paid")
	@NotNull
	private int amountPaid;

	@Column(name = "outstanding_amount")
	@NotNull
	private int outstandingAmount;



}
