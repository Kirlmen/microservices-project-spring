package com.kirl.accounts.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Accounts extends BaseEntity{


	@Column(name = "customer_id")
	private Long customerId;

	@Id
	@Column(name = "account_number")
	private Long accountNumber;//we won't let the system to generate accountNumber here, there will be a specific api for this generation.

	@Column(name = "account_type")
	private String accountType;

	@Column(name = "branch_address")
	private String branchAddress;


}
