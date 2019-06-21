package com.spring.security.oauth.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.spring.security.oauth.entity.CustomUserDetail;
import com.spring.security.oauth.service.CustomUserDetailService;
import com.spring.security.oauth.util.CustomErrorType;
import com.spring.security.oauth.util.SuccessMessage;

@RestController
@CrossOrigin
@RequestMapping("user-service")
public class UserDetailController {
	

	public static Logger logger = LoggerFactory.getLogger(UserDetailController.class);
	@Autowired
	private CustomUserDetailService userDetailService;

	//@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/fetchall", method = RequestMethod.GET)
	public ResponseEntity<List<CustomUserDetail>> listAllUsers() {
		
		logger.info("list all Users Rest API Invoked===================");
		
		List<CustomUserDetail> users = userDetailService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<CustomUserDetail>>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		// ResponseEntity.accepted().body(users);
		// ResponseEntity.ok().body(users);
		return new ResponseEntity<List<CustomUserDetail>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single User-----------------------------

	@RequestMapping(value = "/fetchuser/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
		logger.info("Fetching User with id {}", id);
		CustomUserDetail user = userDetailService.findById(id);
		if (user == null) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CustomUserDetail>(user, HttpStatus.OK);
	}

	// ------------------- Delete a User-----------------------------------------

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		logger.info("Fetching & Deleting User with id {}", id);

		CustomUserDetail user = userDetailService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity<CustomErrorType>(
					new CustomErrorType("Unable to delete. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		userDetailService.deleteUserById(id);
		return new ResponseEntity<String>("User Deleteted Successfully",HttpStatus.OK);
	}

	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody CustomUserDetail user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);

		if (userDetailService.isUserExist(user)) {
			logger.error("Unable to create. A User with name {} already exist", user.getUserName());
			return new ResponseEntity<>(
					new CustomErrorType("Unable to create. A User with Email/Phone " + user.getEmail() +"/"+user.getPhone() + " already exist."),
					HttpStatus.CONFLICT);
		}
		userDetailService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user-service/fetchuser/{id}").buildAndExpand(user.getUserId()).toUri());
		return new ResponseEntity<SuccessMessage>(new SuccessMessage("User with id " + user.getUserId() + " Created"),
				HttpStatus.CREATED);
	}

}
