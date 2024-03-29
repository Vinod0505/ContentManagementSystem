package com.example.cms.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BlogResponse {
	
	private int blogId;
	private String title;
	private String[] topics;
	private String about;
}
