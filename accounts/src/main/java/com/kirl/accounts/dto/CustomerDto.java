package com.kirl.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Schema to holds Customer and Account information")
public class CustomerDto {

	@NotEmpty(message = "Name field cannot be empty or null")
	@Size(min = 3, max = 30, message = "the length of customer name must be minimum 3 characters maximum 10")
	@Schema(description = "name of the Customer", example = "Lucifer Morningstar")
	private String name;

	@NotEmpty(message = "Email field cannot be empty or null")
	@Email(message = "wrong type of email")
	@Schema(description = "email of the Customer", example = "lucifer@gmail.com")
	private String email;

	@Pattern(regexp = "(^$|[0-9]{10})",message = "mobile number must have 11 digits")
	@Schema(description = "mobileNumber of the Customer", example = "4856377925")
	private String mobileNumber;

	@Schema(description = "Account details of the Customer")
	private AccountsDto accountsDto;
}
