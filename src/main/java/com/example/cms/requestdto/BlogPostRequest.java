package com.example.cms.requestdto;

import com.example.cms.enums.PostType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogPostRequest {

	private String title;
	private String subTitle;
	private String summary;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
}
