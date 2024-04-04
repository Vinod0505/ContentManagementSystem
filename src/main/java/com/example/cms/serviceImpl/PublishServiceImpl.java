package com.example.cms.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.model.Publish;
import com.example.cms.model.Schedule;
import com.example.cms.repo.BlogPostRepo;
import com.example.cms.repo.PublishRepo;
import com.example.cms.repo.ScheduleRepo;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.requestdto.ScheduleRequest;
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
	private ScheduleRepo scheduleRepo;

	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,
			int blogPostId) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepo.findById(blogPostId).map(blogPost -> {
			Publish publish = null;
			if(!email.equals(blogPost.getCreatedBy()) && !email.equals(blogPost.getBlog().getUser().getEmail()))
				throw new IllegalAccessRequestException("Faild to publish blogpost");
			if(blogPost.getPublish()!= null) {
				publish =  mapToPublishEntity(publishRequest, blogPost.getPublish());
			}else {
				publish = mapToPublishEntity(publishRequest, new Publish());
			}
			if(publishRequest.getSchedule()!=null) {
				if(publishRequest.getSchedule().getDateTime().isAfter(LocalDateTime.now()))
					throw new IllegalArgumentException("Faild to publish blogpost");
				 publish.setSchedule(scheduleRepo.save(mapToScheduleEntity(publishRequest.getSchedule(),new Schedule())));
				 blogPost.setPostType(PostType.SCHEDULED);
			}
			else {
				blogPost.setPostType(PostType.PUBLISHED);
			}
			publish.setBlogPost(blogPost);
			publishRepo.save(publish);
			return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
					.setMessage("BlogPost published successfully")
					.setBody(mapToPublishResponse(publish)));
		}).orElseThrow(()-> new BlogPostNotFoundByIdException("Faild to publish blogpost"));
	}


	private Schedule mapToScheduleEntity(ScheduleRequest scheduleRequest, Schedule schedule) {
		schedule.setDateTime(scheduleRequest.getDateTime());
				return schedule;
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


	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> unPublishBlogPost(int blogPostId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return blogPostRepo.findById(blogPostId).map( blogPost ->{
			if(!email.equals(blogPost.getCreatedBy()) && !email.equals(blogPost.getBlog().getUser().getEmail()))
				throw new IllegalAccessRequestException("Faild to publish blogpost");
			blogPost.setPostType(PostType.DRAFT);
			if(blogPost.getPostType()!= PostType.PUBLISHED)
				blogPostRepo.save(blogPost);

			return ResponseEntity.ok(responseStructure.setStatus(HttpStatus.OK.value())
					.setMessage("blogPost unpublished suucessfully")
					.setBody(mapToPublishResponse(new Publish())));
		}).orElseThrow(()-> new BlogPostNotFoundByIdException("Failed to unpublish the blog with ID"));

	}
}
