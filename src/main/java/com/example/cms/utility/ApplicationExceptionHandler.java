package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class ApplicationExceptionHandler {

	private ErrorStructure<String> structure;


	private ResponseEntity<ErrorStructure<String>> errorResponse(
			HttpStatus status, String message, String rootCause){
		return new ResponseEntity<ErrorStructure<String>>(structure
				.setStatus(status.value())
				.setMessage(message)
				.setRootCause(rootCause), status);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistsByEmail(
			UserAlreadyExistByEmailException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"User Already exists with the given email Id");
	}
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserNotFoundById(
			UserNotFoundByIdException ex){
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), 
				"User Not exists with the given Id");
	}

}
