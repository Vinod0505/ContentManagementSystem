package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PublishController {

	private PublishService publishService;
	
	@Operation(description = "This endpoint is used to publish the blogPost ", responses = {
			@ApiResponse(responseCode = "200", description = "BlogPost Published successfully"),
			@ApiResponse(responseCode = "404", description = "Failed to Publish Blogpost",
			content = @Content(schema = @Schema(implementation = ErrorStructure.class)))
	})
	@PostMapping("/blog-posts/{blogPostId}/publishes")
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(@RequestBody PublishRequest publishRequest,@PathVariable int blogPostId){
		return publishService.publishBlogPost(publishRequest,blogPostId);
	}
	
	@Operation(description = "This endpoint is used to unpublish the blogPost ", responses = {
			@ApiResponse(responseCode = "200", description = "BlogPost unpublished successfully"),
			@ApiResponse(responseCode = "404", description = "Failed to unpublish Blogpost",
			content = @Content(schema = @Schema(implementation = ErrorStructure.class)))
	})
	@PutMapping("/blog-posts/{blogPostId}/publishes")
	public ResponseEntity<ResponseStructure<PublishResponse>> unPublishBlogPost(@PathVariable int blogPostId){
		return publishService.unPublishBlogPost(blogPostId);
	}
	
}
