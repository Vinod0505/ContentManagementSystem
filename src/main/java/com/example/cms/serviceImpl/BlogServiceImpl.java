package com.example.cms.serviceImpl;

import org.assertj.core.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.TitleAlphabetsOnlyException;
import com.example.cms.exception.TitleAlreadyExistsException;
import com.example.cms.exception.TopicsNotSpecifiedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.Blog;
import com.example.cms.model.ContributionPanel;
import com.example.cms.repo.BlogRepo;
import com.example.cms.repo.ContributionPanelRepo;
import com.example.cms.repo.UserRepository;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService{

	private UserRepository userRepo;
	private BlogRepo blogRepo;
	private ResponseStructure<BlogResponse> responseStructure;
	private ContributionPanelRepo contributionPanelRepo;


	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(int userId, BlogRequest blogRequest) {
		return userRepo.findById(userId).map(user -> {
			validateBlogRequest(blogRequest);
			Blog blog = mapToBlogEntity(blogRequest, new Blog());
			ContributionPanel contributionPanel = new ContributionPanel();
			contributionPanelRepo.save(contributionPanel);
			blog.setContributionPanel(contributionPanel);
			blog.setUser(user);
			blogRepo.save(blog);
			return ResponseEntity.ok(responseStructure
					.setStatus(HttpStatus.OK.value())
					.setMessage("Blog created successfully")
					.setBody(mapToBlogResponse(blog)));
		}).orElseThrow(()-> new UserNotFoundByIdException("Faild to create blog"));
	}

	private Blog mapToBlogEntity(BlogRequest blogRequest, Blog blog) {
		blog.setTitle(blogRequest.getTitle());
		blog.setTopics(blogRequest.getTopics());
		blog.setAbout(blogRequest.getAbout());
		return blog;
	}

	private BlogResponse mapToBlogResponse(Blog blog) {
		return new BlogResponse(
				blog.getBlogId(),
				blog.getTitle(),
				blog.getTopics(),
				blog.getAbout());
	}

	@Override
	public ResponseEntity<ResponseStructure<Boolean>> checkForBlog(String title) {
		return ResponseEntity.ok(new ResponseStructure<Boolean>()
				.setStatus(HttpStatus.OK.value())
				.setMessage("Blog title details fetched successfully")
				.setBody(blogRepo.existsByTitle(title)));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> findByBlogId(int blogId) {
		return blogRepo.findById(blogId).map(blog -> {
			return ResponseEntity.ok(responseStructure
					.setStatus(HttpStatus.FOUND.value())
					.setMessage("Blog found successfully")
					.setBody(mapToBlogResponse(blog))
					);
		}).orElseThrow(()->new BlogNotFoundByIdException("Blog not Found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlog(int blogId, BlogRequest blogRequest) {
		return blogRepo.findById(blogId).map(blog -> {
			validateBlogRequest(blogRequest);
			blog = blogRepo.save(mapToBlogEntity(blogRequest, blog));
			return ResponseEntity.ok(responseStructure
					.setStatus(HttpStatus.OK.value())
					.setMessage("Blog updated successfully")
					.setBody(mapToBlogResponse(blog)));
		}).orElseThrow(()-> new BlogNotFoundByIdException("Faild to update blog"));
	}

	private void validateBlogRequest(BlogRequest blogRequest) {
		if(!blogRequest.getTitle().matches("[a-zA-Z ]+") || blogRequest.getTitle()==null)
			throw new TitleAlphabetsOnlyException("Faild to update blog");

		if(blogRepo.existsByTitle(blogRequest.getTitle()))
			throw new TitleAlreadyExistsException("Faild to update blog");

		if(blogRequest.getTopics().length<1)
			throw new TopicsNotSpecifiedException("Faild to update blog");

	}
}
