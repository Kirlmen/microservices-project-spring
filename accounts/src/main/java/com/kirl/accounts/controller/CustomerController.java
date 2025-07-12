package com.kirl.accounts.controller;

import com.kirl.accounts.constants.AccountsConstants;
import com.kirl.accounts.dto.CustomerDetailsDto;
import com.kirl.accounts.dto.ErrorResponseDto;
import com.kirl.accounts.dto.ResponseDto;
import com.kirl.accounts.service.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated

@Tag(
		name = "REST APIs for Customers in EazyBank",
		description = "crud operations of EazyBank to FETCH Customer details"
)
public class CustomerController {

	private final ICustomersService customersService;

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	public CustomerController(ICustomersService customersService) {
		this.customersService = customersService;
	}

	@Operation(
			summary = "Fetch Customer Details REST API",
			description = "REST API to fetch Customer details based on a mobile number"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "HTTP Status OK"
			),
			@ApiResponse(
					responseCode = "500",
					description = "HTTP Status Internal Server Error",
					content = @Content(
							schema = @Schema(implementation = ErrorResponseDto.class)
					)
			)
	})
	@GetMapping("/fetchCustomerDetails")
	ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("eazybank-correlation-id") String correlationId,
	                                                        @RequestParam
	                                                        @Pattern(regexp = "(^$|[0-9]{10})", message = "mobileNumber must be 10 digits")
	                                                        String mobileNumber) {
		logger.debug("eazybank-correlation-id found: {}", correlationId);
		return ResponseEntity.status(HttpStatus.OK).body(customersService.fetchCustomerDetails(mobileNumber, correlationId));
	}
}

