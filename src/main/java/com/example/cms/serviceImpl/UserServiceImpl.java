package com.example.cms.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.User;
import com.example.cms.repo.UserRepository;
import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private ResponseStructure<UserResponse> structure;
	private PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest) {
		if(userRepository.existsByEmail(userRequest.getEmail()))
			throw new UserAlreadyExistByEmailException("Faild to register user");
		
		User user = userRepository.save(mapToUserEntity(userRequest, new User()));
		return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
				.setMessage("User registered successfully")
				.setBody(mapToUserResponse(user)));
	}
	
//	private UserResponse mapToUserResponse(User user) {
//		return UserResponse.builder()
//				.userId(user.getUserId())
//				.username(user.getUsername())
//				.email(user.getEmail())
//				.build();
//	}
	
	private UserResponse mapToUserResponse(User user) {
	    return new UserResponse(user.getUserId(), 
	    		user.getUserName(),
	    		user.getEmail(),
	    		user.getCreatedAt(),
	    		user.getLastModifiedAt());
	}
	
	private User mapToUserEntity(UserRequest userRequest, User user) {
		user.setEmail(userRequest.getEmail());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setUserName(userRequest.getUserName());
		user.setDeleted(false);
		return user;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId) {
		return userRepository.findById(userId).map(user -> {
			user.setDeleted(true);
			userRepository.save(user);
			return ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
					.setMessage("User Deleted Temporaryly")
					.setBody(mapToUserResponse(user))
					);})
		.orElseThrow(()-> new UserNotFoundByIdException("User Not Found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findByUserId(int userId) {
		return userRepository.findById(userId).map(user -> ResponseEntity.ok(structure.setStatus(HttpStatus.OK.value())
				.setMessage("User found successfully")
				.setBody(mapToUserResponse(user)))).orElseThrow(()-> new UserNotFoundByIdException("User Not found"));
	}
}
