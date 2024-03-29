package com.example.cms.responsedto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserResponse {

	private int userId;
	private String userName;
	private String email;
	private LocalDate createdAt;
	private LocalDateTime lastModifiedAt;
}
