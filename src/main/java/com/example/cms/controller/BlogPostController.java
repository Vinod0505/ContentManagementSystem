package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BlogPostController {

	private BlogPostService blogPostservice;


	@Operation(description = "This endpoint is used to create draft for blogPost ", responses = {
			@ApiResponse(responseCode = "200", description = "Draft created for blogPost successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input")
	})
	@PostMapping("/blogs/{blogId}/blog-posts")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(@PathVariable int blogId, @RequestBody BlogPostRequest blogPostRequest){
		return blogPostservice.createDraft(blogId,blogPostRequest);
	}


	@Operation(description = "This endpoint is used to update draft for blogPost ", responses = {
			@ApiResponse(responseCode = "200", description = "Draft updated  successfully"),
			@ApiResponse(responseCode = "404", description = "BlogPost not exists with the given Id", content = {
					@Content(schema = @Schema(implementation = ErrorStructure.class))	
			})
	})
	@PutMapping("/blog-posts/{blogPostId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(@RequestBody BlogPostRequest blogPostRequest, @PathVariable int blogPostId){
		return blogPostservice.updateDraft(blogPostRequest,blogPostId);
	}
	
	@Operation(description = "This endpoint is used to delete blogPost ", responses = {
			@ApiResponse(responseCode = "200", description = "Deleted blogPost successfully"),
			@ApiResponse(responseCode = "404", description = "blogPost not exists with given Id", content = {
					@Content(schema = @Schema(implementation = ErrorStructure.class))	
			})
	})
	@DeleteMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(@PathVariable int blogPostId){
		return blogPostservice.deleteBlogPost(blogPostId);
	}
}
