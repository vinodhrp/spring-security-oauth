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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Address")
@Table(name = "USR_ADDRESS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ADDRS_ID")
	private Long addrsId;

	@Column(name = "STREET_NAME")
	private String streetName;

	@Column(name = "STREET_NO")
	private String streetNo;

	@Column(name = "CITY")
	private String city;

	@Column(name = "ZIP_CODE")
	private String zipcode;

	@Column(name = "COUNTRY")
	private String country;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "USR_ID")
	private CustomUserDetail userDetail;

}
