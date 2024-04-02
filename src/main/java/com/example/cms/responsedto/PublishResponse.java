package com.example.cms.responsedto;

import com.example.cms.enums.PostType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PublishResponse {

	private int publishId;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	private PostType postType;
}
