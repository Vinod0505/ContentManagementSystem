package com.example.cms.responsedto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

	private int userId;
	private String username;
	private String email;
	private LocalDate createdAt;
	private LocalDate lastModifiedAt;
}
