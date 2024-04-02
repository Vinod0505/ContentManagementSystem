package com.example.cms.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublishRequest {

	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
}
