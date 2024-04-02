package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogPostService {

	ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(int blogId, BlogPostRequest blogPostRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(BlogPostRequest blogPostRequest, int blogPostId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int blogPostId);

}
