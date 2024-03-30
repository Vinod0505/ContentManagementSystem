package com.example.cms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.model.ContributionPanel;
import com.example.cms.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	boolean existsByEmail(String email);


	Optional<User> findByEmail(String username);

}
