package com.spring.security.oauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.oauth.entity.AuthGroup;

@Repository
public interface AuthGroupRespository  extends JpaRepository<AuthGroup, Long>{
	
	public List<AuthGroup> findByEmail(String email);

}
