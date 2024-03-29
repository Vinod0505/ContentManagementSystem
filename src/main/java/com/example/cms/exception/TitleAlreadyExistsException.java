package com.example.cms.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TitleAlreadyExistsException extends RuntimeException {

	private String message;

	public String getMessage() {
		return message;
	}
}
