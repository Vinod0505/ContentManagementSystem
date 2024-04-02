package com.example.cms.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Publish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int publishId;
	@Column(nullable = false)
	@NotEmpty
	@NotBlank
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	
	@OneToOne
	private BlogPost blogPost;
	
}
