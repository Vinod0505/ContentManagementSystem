package com.example.cms.responsedto;

import java.time.LocalDateTime;

import com.example.cms.enums.PostType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlogPostResponse {
	
	private int blogPostId;
	private String title;
	private String subTitle;
	private  String summary;
	private PostType postType;
//	private String seoTitle;
//	private String seoDescription;
//	private String[] seoTopics;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime lastModifiedAt;
	private String lastModifiedBy;

}
