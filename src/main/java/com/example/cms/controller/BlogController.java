package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

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
}