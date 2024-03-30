package com.example.cms.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PanelNotFoundByIdException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.ContributionPanel;
import com.example.cms.repo.BlogRepo;
import com.example.cms.repo.ContributionPanelRepo;
import com.example.cms.repo.UserRepository;
import com.example.cms.responsedto.ContributionPanelResponse;
import com.example.cms.service.ContributionPanelService;
import com.example.cms.utility.ResponseStructure;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class ContributionPanelServiceImpl implements ContributionPanelService{

	private UserRepository userRepo;

	private BlogRepo blogRepo;

	private ContributionPanelRepo contributionPanelRepo;

	private ResponseStructure<ContributionPanelResponse> responseStructure;

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> addContributors(int userId, int panelId) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();


		return	userRepo.findByEmail(email).map(owner->{
			return contributionPanelRepo.findById(panelId).map(panel->{
				if(!blogRepo.existsByUserAndContributionPanel(owner, panel))
					throw new IllegalAccessRequestException("failed to add contributor");
				return userRepo.findById(userId).map(contributor->{
					if(!panel.getUsers().contains(contributor)) {
						panel.getUsers().add(contributor);
					contributionPanelRepo.save(panel);
					}	return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
							.setMessage("Contributor added succesffuly")
							.setBody(mapToContributionPanelResponse(panel)));
				}).orElseThrow(()-> new UserNotFoundByIdException("User not found"));
			}).orElseThrow(()-> new PanelNotFoundByIdException("Panel not found"));
		}).get();

	}
	private ContributionPanelResponse mapToContributionPanelResponse(ContributionPanel panel) {

		return new ContributionPanelResponse(panel.getPanelId());
	}
	@Override
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> removeUser(int userId, int panelId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return	userRepo.findByEmail(email).map(owner->{
			return contributionPanelRepo.findById(panelId).map(panel->{
				if(!blogRepo.existsByUserAndContributionPanel(owner, panel))
					throw new IllegalAccessRequestException("failed to remove contributor");
				return userRepo.findById(userId).map(contributor->{
					panel.getUsers().remove(contributor);
					contributionPanelRepo.save(panel);
					return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
							.setMessage("Contributor removed succesffuly")
							.setBody(mapToContributionPanelResponse(panel)));
				}).orElseThrow(()-> new UserNotFoundByIdException("User not found"));
			}).orElseThrow(()-> new PanelNotFoundByIdException("Panel not found"));
		}).get();
	}

}
