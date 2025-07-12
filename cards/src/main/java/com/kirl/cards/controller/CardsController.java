package com.kirl.cards.controller;

import com.kirl.cards.constants.CardsConstants;
import com.kirl.cards.dto.CardsContactInfoDto;
import com.kirl.cards.dto.CardsDto;
import com.kirl.cards.dto.ErrorResponseDto;
import com.kirl.cards.dto.ResponseDto;
import com.kirl.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
		name = "CRUD REST APIs for Cards in EazyBank",
		description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

	ICardsService iCardsService;

	private static final Logger logger = LoggerFactory.getLogger(CardsController.class);


	@Autowired
	CardsContactInfoDto contactInfoDto;

	@Value("${build.version}")
	private String buildVersion;

	@Autowired
	public CardsController(ICardsService iCardsService) {
		this.iCardsService = iCardsService;
	}

	@Operation(
			summary = "Create Card REST API",
			description = "REST API to create new Card inside EazyBank"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "HTTP Status CREATED"
			),
			@ApiResponse(
					responseCode = "400",
					description = "HTTP Status BAD_REQUEST"
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
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createCards(@RequestParam
	                                               @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	                                               String mobileNumber) {
		iCardsService.createCards(mobileNumber);
		return ResponseEntity.status(
						HttpStatus.CREATED)
				.body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));

	}

	@Operation(
			summary = "Fetch Card Details REST API",
			description = "REST API to fetch card details based on a mobile number"
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
	@GetMapping("/fetch")
	public ResponseEntity<CardsDto> fetchCards(@RequestHeader("eazybank-correlation-id") String correlationId,
			@RequestParam
	                                           @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	                                           String mobileNumber) {
		logger.debug("eazybank-correlation-id found: {}", correlationId);
		return ResponseEntity.status(HttpStatus.OK).body(iCardsService.fetchCards(mobileNumber));
	}

	@Operation(
			summary = "Update Card Details REST API",
			description = "REST API to update card details based on a card number"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "HTTP Status OK"
			),
			@ApiResponse(
					responseCode = "417",
					description = "Expectation Failed"
			),
			@ApiResponse(
					responseCode = "500",
					description = "HTTP Status Internal Server Error",
					content = @Content(
							schema = @Schema(implementation = ErrorResponseDto.class)
					)
			)
	})
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateCards(@RequestBody CardsDto cardsDto) {
		boolean res = iCardsService.updateCards(cardsDto);
		if (res) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE)
			);
		}
	}

	@Operation(
			summary = "Delete Card Details REST API",
			description = "REST API to delete Card details based on a mobile number"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "HTTP Status OK"
			),
			@ApiResponse(
					responseCode = "417",
					description = "Expectation Failed"
			),
			@ApiResponse(
					responseCode = "500",
					description = "HTTP Status Internal Server Error",
					content = @Content(
							schema = @Schema(implementation = ErrorResponseDto.class)
					)
			)
	})
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteCards(@RequestParam
	                                               @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	                                               String mobileNumber) {
		boolean res = iCardsService.deleteCards(mobileNumber);
		if (res) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
		}
	}

	@Operation(
			summary = "Get Card Info REST API",
			description = "REST API to getting card ms contact details"
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
	@GetMapping("/contact-info")
	public ResponseEntity<CardsContactInfoDto> getCardsInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(contactInfoDto);
	}

	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo(){
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(buildVersion);
	}
}
