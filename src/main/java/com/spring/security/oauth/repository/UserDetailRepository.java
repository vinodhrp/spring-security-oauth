package com.spring.security.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.oauth.entity.CustomUserDetail;

@Repository
public interface UserDetailRepository extends JpaRepository<CustomUserDetail, Long> {
	
	
	public CustomUserDetail findByUserName(String userName);
	
	public CustomUserDetail findByEmail(String email);

}
