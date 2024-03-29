package com.example.cms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.model.Blog;

public interface BlogRepo extends JpaRepository<Blog, Integer> {

	boolean existsByTitle(String title);
}
