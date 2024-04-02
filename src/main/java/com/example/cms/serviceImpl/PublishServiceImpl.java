package com.example.cms.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.model.Publish;
import com.example.cms.repo.BlogPostRepo;
import com.example.cms.repo.PublishRepo;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublishServiceImpl implements PublishService{

	private PublishRepo publishRepo;
	private BlogPostRepo blogPostRepo;
	private ResponseStructure<PublishResponse> responseStructure;

	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,
			int blogPostId) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return blogPostRepo.findById(blogPostId).map(blogPost -> {
			if(!email.equals(blogPost.getCreatedBy()) && !email.equals(blogPost.getBlog().getUser().getEmail()))
				throw new IllegalAccessRequestException("Faild to publish blogpost");
			blogPost.setPostType(PostType.PUBLISHED);
			Publish publish =  publishRepo.save(mapToPublishEntity(publishRequest, new Publish()));
			publish.setBlogPost(blogPost);
			return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
					.setMessage("BlogPost published successfully")
					.setBody(mapToPublishResponse(publish)));
		}).orElseThrow(()-> new BlogPostNotFoundByIdException("Faild to publish blogpost"));
	}



	private Publish mapToPublishEntity(PublishRequest publishRequest, Publish publish) {
		publish.setSeoTitle(publishRequest.getSeoTitle());
		publish.setSeoDescription(publishRequest.getSeoDescription());
		publish.setSeoTopics(publishRequest.getSeoTopics());
		return publish;
	}

	private PublishResponse mapToPublishResponse(Publish publish) {
		return new PublishResponse(
				publish.getPublishId(),
				publish.getSeoTitle(),
				publish.getSeoDescription(),
				publish.getSeoTopics(),
				PostType.PUBLISHED);
	}

}
