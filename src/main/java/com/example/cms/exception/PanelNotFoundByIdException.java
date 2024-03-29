package com.example.cms.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PanelNotFoundByIdException extends RuntimeException{

	private String message;

	@Override
	public String getMessage() {
		return message;
	}
}
