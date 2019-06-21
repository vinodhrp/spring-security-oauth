package com.spring.security.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.oauth.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
