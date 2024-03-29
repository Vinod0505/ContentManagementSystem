package com.example.cms.requestdto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class BlogRequest {

	@NotNull
	private String title;
	private String[] topics;
	private String about;
}
