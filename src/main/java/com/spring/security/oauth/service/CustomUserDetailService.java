package com.spring.security.oauth.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.security.oauth.entity.CustomUserDetail;
import com.spring.security.oauth.repository.UserDetailRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
	
	@Autowired
	private UserDetailRepository userDetailRepository;

	//@Autowired
	//private AuthGroupRespository authGroupRespository;

	public List<CustomUserDetail> findAllUsers() {
		return userDetailRepository.findAll();
	}

	public CustomUserDetail findById(Long id) {
		Optional<CustomUserDetail> user = userDetailRepository.findById(id);
		if (user.isPresent())
			return user.get();
		return null;
	}

	public void saveUser(CustomUserDetail user) {
		userDetailRepository.save(user);
	}

	public void deleteUserById(Long id) {
		userDetailRepository.deleteById(id);
	}

	public void deleteUser(CustomUserDetail user) {
		userDetailRepository.delete(user);
	}

	public boolean isUserExist(CustomUserDetail user) {

		boolean isUserAvail = false;

		if (user.getUserId() != null) {
			return isUserAvail;
		}

		CustomUserDetail phoneUser = new CustomUserDetail();
		phoneUser.setPhone(user.getPhone());

		CustomUserDetail userEmail = new CustomUserDetail();
		userEmail.setPhone(user.getEmail());

		Example<CustomUserDetail> phoneEx = Example.of(phoneUser);
		Example<CustomUserDetail> emailEx = Example.of(userEmail);

		Optional<CustomUserDetail> emailUsr = userDetailRepository.findOne(phoneEx);
		Optional<CustomUserDetail> phoneUsr = userDetailRepository.findOne(emailEx);

		if (emailUsr.isPresent() || phoneUsr.isPresent()) {
			isUserAvail = true;
		}

		return isUserAvail;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		CustomUserDetail user = userDetailRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Email not found");
		}

		//List<AuthGroup> authUsr = authGroupRespository.findByEmail(email);
		logger.info("loadUserByUsername Invoked in Service Layer===============>" +user);
		
		return new User(user.getUsername(),user.getPassword(),user.getAuthorities());
	}

}
