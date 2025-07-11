package com.kirl.accounts.exception;

import com.kirl.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String,String> validationErrors = new HashMap<>(); //we will put the errors here that spring threw
		List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors(); //errors that spring throws,

		validationErrorList.forEach(error->{ //populating the hashmap with the given errors to send to the client
			String fieldName = ((FieldError)error).getField();
			String errorMsg = error.getDefaultMessage();
			validationErrors.put(fieldName,errorMsg);
		});
		return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	ResponseEntity<ErrorResponseDto> handleGlobalException(
			Exception exception,
			WebRequest webRequest){
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(
				webRequest.getDescription(false), //this will send only apiPath which our DTO Class needs.
				HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage(),
				LocalDateTime.now()
		);
		return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomerAlreadyExistException.class)
	ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistException(
			CustomerAlreadyExistException exception,
			WebRequest webRequest){
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(
				webRequest.getDescription(false), //this will send only apiPath which our DTO Class needs.
				HttpStatus.BAD_REQUEST,
				exception.getMessage(),
				LocalDateTime.now()
		);
		return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(
				webRequest.getDescription(false),
				HttpStatus.NOT_FOUND,
				exception.getMessage(),
				LocalDateTime.now()
		);
				return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
	}
}
