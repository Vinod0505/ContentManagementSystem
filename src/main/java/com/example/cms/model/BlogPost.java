package com.example.cms.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.cms.enums.PostType;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Setter
@Getter
public class BlogPost {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blogPostId;
	private String title;
	private String subTitle;
	private  String summary;
	private PostType postType;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	@ManyToOne
	private Blog blog;
	
}
