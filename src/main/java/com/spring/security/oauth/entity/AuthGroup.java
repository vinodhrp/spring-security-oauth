package com.spring.security.oauth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "AuthGroup")
@Table(name = "AUTH_USER_GROUP")
public class AuthGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "auth_user_group_id")
	private Long authUsrGrpId;

	@Column(name = "usr_email")
	private String email;

	@Column(name = "auth_group")
	private String authGrp;
	
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "USR_ID")
	private CustomUserDetail userDetail;

	public Long getAuthUsrGrpId() {
		return authUsrGrpId;
	}

	public void setAuthUsrGrpId(Long authUsrGrpId) {
		this.authUsrGrpId = authUsrGrpId;
	}

	

	public String getAuthGrp() {
		return authGrp;
	}

	public void setAuthGrp(String authGrp) {
		this.authGrp = authGrp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
