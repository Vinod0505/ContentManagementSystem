package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@AllArgsConstructor
public class UserController {

	private UserService userService;
	
	@Operation(description = "This endpoint is used to add new user to the database", responses = {
			@ApiResponse(responseCode = "200", description = "User registerd successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input")
	})
	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody UserRequest userRequest){
		return userService.registerUser(userRequest);
	}
	
	@GetMapping("/test")
	public String test() {
		return "Hello from cms";
	}
	
	@Operation(description = "This endpoint will fetch user from the database based on id", responses = {
			@ApiResponse(responseCode = "200", description = "User found successfully"),
			@ApiResponse(responseCode = "404", description = "User not exist by the given id", content = {
					@Content(schema = @Schema(implementation = ErrorStructure.class))	
			})
	})
	@GetMapping("users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findByUserId(@PathVariable int userId){
		return userService.findByUserId(userId);
	}
}
