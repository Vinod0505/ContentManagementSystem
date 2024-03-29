package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.TitleAlphabetsOnlyException;
import com.example.cms.exception.TitleAlreadyExistsException;
import com.example.cms.exception.TopicsNotSpecifiedException;
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
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleTitleAlphabetsOnly(
			TitleAlphabetsOnlyException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"Title should only contain alphabets");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleTitleAlreadyExists(
			TitleAlreadyExistsException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"The title should be unique");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleTopicsNotSpecified(
			TopicsNotSpecifiedException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"At list one topic has to be specified");
	}
}

