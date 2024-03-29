package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@AllArgsConstructor
public class BlogController {
	
	private BlogService blogService;

	
	
	@Operation(description = "This endpoint is used to add new blog to the database", responses = {
			@ApiResponse(responseCode = "200", description = "Blog created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input")
	})
	@PostMapping("/users/{userId}/blogs")
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(@PathVariable int userId, @RequestBody BlogRequest blog){
		return blogService.createBlog(userId, blog);
	}
	
	@Operation(description = "This endpoint is used to check the blog title availability", responses = {
			@ApiResponse(responseCode = "200", description = "Blog created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input")
	})
	@GetMapping("/titles/{title}/blogs")
	public ResponseEntity<ResponseStructure<Boolean>> checkForBlog(@PathVariable String title){
		return blogService.checkForBlog(title);
	}
	
	@Operation(description = "This endpoint will fetch blog from the database based on id", responses = {
			@ApiResponse(responseCode = "200", description = "Blog found successfully"),
			@ApiResponse(responseCode = "404", description = "Blog not exist by the given id", content = {
					@Content(schema = @Schema(implementation = ErrorStructure.class))	
			})
	})
	@GetMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> findByBlogId(@PathVariable int blogId){
		return blogService.findByBlogId(blogId);
	}

}