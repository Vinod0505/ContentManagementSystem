package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.responsedto.ContributionPanelResponse;
import com.example.cms.utility.ResponseStructure;

public interface ContributionPanelService {

	ResponseEntity<ResponseStructure<ContributionPanelResponse>> addContributors(int userId, int panelId);

	ResponseEntity<ResponseStructure<ContributionPanelResponse>> removeUser(int userId, int panelId);

}
