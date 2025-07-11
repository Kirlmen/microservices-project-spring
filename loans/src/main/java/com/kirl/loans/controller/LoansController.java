package com.kirl.loans.controller;

import com.kirl.loans.constants.LoansConstants;
import com.kirl.loans.dto.ErrorResponseDto;
import com.kirl.loans.dto.LoansContactInfoDto;
import com.kirl.loans.dto.LoansDto;
import com.kirl.loans.dto.ResponseDto;
import com.kirl.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
		name = "CRUD REST APIs for Loans in EazyBank",
		description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
@Validated
public class LoansController {

	private final ILoansService iLoansService;

	@Value("${build.version}")
	private String buildVersion;

	@Autowired
	private LoansContactInfoDto contactInfoDto;
	@Autowired
	private Environment environment;

	@Autowired
	public LoansController(ILoansService iLoansService) {
		this.iLoansService = iLoansService;
	}

	@Operation(
			summary = "Create Loan REST API",
			description = "REST API for creating a loan in the Bank DB with given mobile number")
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "HTTP Status CREATED"
			),
			@ApiResponse(
					responseCode = "400",
					description = "HTTP Status BAD REQUEST",
					content = @Content(
							schema = @Schema(implementation = ErrorResponseDto.class)
					)
			)
	})
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createLoans(@RequestParam
	                                              @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	                                              String mobileNumber) {
		iLoansService.createLoan(mobileNumber);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
	}

	@GetMapping("/fetch")
	public ResponseEntity<LoansDto> fetchLoans(@RequestParam
	                                          @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	                                          String mobileNumber) {
		LoansDto loansDto = iLoansService.fetchLoans(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(loansDto);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateLoans(@Valid @RequestBody LoansDto loansDto) {
		boolean condition = iLoansService.updateLoans(loansDto);
		if (condition) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		} else {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteLoans(@RequestParam
	                                              @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	                                              String mobileNumber) {
		boolean condition = iLoansService.deleteLoans(mobileNumber);
		if (condition) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		} else {
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
		}
	}

	@Operation(
			summary = "Get Build information",
			description = "Get Build information that is deployed into cards microservice"
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
	}
	)

	@GetMapping("build-info")
	public ResponseEntity<String> getBuildVersion() {
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}

	@Operation(
			summary = "Get Java version",
			description = "Get Java versions details that is installed into cards microservice"
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
	}
	)
	@GetMapping("java-version")
	public ResponseEntity<String> getJavaVersion() {
		return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
	}


	@Operation(
			summary = "Get Contact Info",
			description = "Contact Info details that can be reached out in case of any issues"
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
	}
	)
	@GetMapping("/contact-info")
	public ResponseEntity<LoansContactInfoDto> getContactInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(contactInfoDto);
	}

}
