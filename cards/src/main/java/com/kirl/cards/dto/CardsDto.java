package com.kirl.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(name = "Cards",
		description = "Schema to hold Card information"
)
public class CardsDto {

	@Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
	@Schema(description = "Mobile Number of Customer", example = "4354437687")
	public String mobileNumber;

	@Pattern(regexp = "(^$|[0-9]{12})",message = "Card Number must be 12 digits")
	@Schema(description = "Card Number of Customer", example = "100646930341")
	public String cardNumber;

	@NotNull(message = "Card Type cannot be empty or null")
	@Schema(description = "Type of the card",example = "Credit Card")
	public String cardType;

	@Positive(message = "Total card limit should be greater than zero")
	@Schema(
			description = "Total amount limit available against a card", example = "100000"
	)
	public int totalLimit;

	@PositiveOrZero(message = "Total amount used should be equal or greater than zero")
	@Schema(
			description = "Total amount used by a Customer", example = "1000"
	)
	public int amountUsed;

	@PositiveOrZero(message = "Total available amount should be equal or greater than zero")
	@Schema(
			description = "Total available amount against a card", example = "90000"
	)
	public int availableAmount;


}
