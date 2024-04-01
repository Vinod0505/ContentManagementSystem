package com.example.cms.serviceImpl;

import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.model.BlogPost;
import com.example.cms.repo.BlogPostRepo;
import com.example.cms.repo.BlogRepo;
import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogPostServiceImpl implements BlogPostService{


	private BlogPostRepo blogPostRepo;

	private BlogRepo blogRepo;

	private ResponseStructure<BlogPostResponse> responseStructure;	

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(int blogId,
			BlogPostRequest blogPostRequest) {
		return blogRepo.findById(blogId).map(blog -> {
			BlogPost blogPost = mapToBlogPostEntity(blogPostRequest, new BlogPost());
			blogPost.setBlog(blog);
			blogPost = blogPostRepo.save(blogPost);
			blog.getBlogPosts().add(blogPost);
			blogRepo.save(blog);
			return ResponseEntity.ok(
					responseStructure.setStatus(HttpStatus.OK.value())
					.setMessage("BlogPost drafted succesffuly")
					.setBody(mapToBlogPostResponse(blogPost))
					);
		}).orElseThrow(()->new BlogNotFoundByIdException("Failed to create Draft"));
	}


	private BlogPost mapToBlogPostEntity(BlogPostRequest blogPostRequest, BlogPost blogPost) {
		blogPost.setTitle(blogPostRequest.getTitle());
		blogPost.setSubTitle(blogPostRequest.getSubTitle());
		blogPost.setSummary(blogPostRequest.getSummary());
		return blogPost;
	}

	private BlogPostResponse mapToBlogPostResponse(BlogPost blogPost) {
		return new BlogPostResponse(
				blogPost.getBlogPostId(),
				blogPost.getTitle(),
				blogPost.getSubTitle(),
				blogPost.getSummary(),
				blogPost.getPostType()
				);

	}


	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(BlogPostRequest blogPostRequest,
			int blogPostId) {
		return blogPostRepo.findById(blogPostId).map(blogPost->{
			blogPost=blogPostRepo.save(mapToBlogPostEntity(blogPostRequest, blogPost));
			return ResponseEntity.ok(
					responseStructure.setStatus(HttpStatus.OK.value())
					.setMessage("BlogPost draft updated successfully")
					.setBody(mapToBlogPostResponse(blogPost))
					);

		}).orElseThrow(()-> new BlogPostNotFoundByIdException("BlogPost Not Found"));
	}

}
