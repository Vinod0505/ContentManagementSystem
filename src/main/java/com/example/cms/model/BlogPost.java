package com.example.cms.model;

import java.time.LocalDateTime; 

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.cms.enums.PostType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	@Column(length = 2500)
	private  String summary;
	private PostType postType;
//	private String seoTitle;
//	private String seoDescription;
//	private String[] seoTopics;
	@ManyToOne
	private Blog blog;
	@CreatedBy
	private String createdBy;
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;
	@LastModifiedBy
	private String lastModifiedBy;
	@LastModifiedDate
	private LocalDateTime lastModifiedAt;
	
	@OneToOne
	private Publish publish;
}
