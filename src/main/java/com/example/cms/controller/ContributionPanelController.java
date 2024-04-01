package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.responsedto.ContributionPanelResponse;
import com.example.cms.service.ContributionPanelService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ContributionPanelController {

	private ContributionPanelService contributionPanelService;
	
	@Operation(description = "This endpoint is used to  addContributors in the database", responses = {
			@ApiResponse(responseCode = "200", description = "contributors added successfully"),
			@ApiResponse(responseCode = "400", description = "invalid inputs")
	})
	@PutMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> addContributors(@PathVariable int userId,@PathVariable int panelId){
		return contributionPanelService.addContributors(userId,panelId);
	}
	
	@Operation(description = "This endpoint is used to  remove User from ContributionPanel in the database", responses = {
			@ApiResponse(responseCode = "200", description = "User removed successfully from contributionPanel"),
			@ApiResponse(responseCode = "404", description = "Failed to remove user from contributionPanel", content = {
					@Content(schema = @Schema(implementation = ErrorStructure.class))	
			})
	})
	@DeleteMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> removeUser(@PathVariable int userId,@PathVariable int panelId){
		return contributionPanelService.removeUser(userId,panelId);
	}
}
