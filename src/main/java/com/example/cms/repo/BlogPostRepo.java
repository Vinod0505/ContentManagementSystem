package com.example.cms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.model.BlogPost;

public interface BlogPostRepo extends JpaRepository<BlogPost, Integer>{

}
