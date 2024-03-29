package com.example.cms.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAlreadyExistByEmailException extends RuntimeException {

	private String message;

	@Override
	public String getMessage() {
		return message;
	}
}
