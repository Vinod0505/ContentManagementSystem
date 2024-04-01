package com.example.cms.repo;

import org.springframework.data.jpa.repository.JpaRepository;  

import com.example.cms.model.ContributionPanel;
import com.example.cms.model.User;

public interface ContributionPanelRepo extends JpaRepository<ContributionPanel, Integer>{

//	boolean existsByPanelIdAndContributors(int panelId, User user);

	boolean existsByPanelIdAndContributors(ContributionPanel contributionPanel, User user);


}
