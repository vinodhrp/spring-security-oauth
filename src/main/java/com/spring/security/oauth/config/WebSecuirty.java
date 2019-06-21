package com.spring.security.oauth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.security.oauth.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecuirty extends WebSecurityConfigurerAdapter {
	
	private Logger log = LoggerFactory.getLogger(WebSecuirty.class);

	@Autowired
	private CustomUserDetailService customUserDetailService;

	/**
	 * User to encode the passwords
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder encodePassword() {
		log.info("BCryptPasswordEncoder Has been Invoked =================> ");
		return new BCryptPasswordEncoder(11);
	}

	public DaoAuthenticationProvider authenticationProvider() {
		log.info("DaoAuthenticationProvider Has been Invoked =================> ");
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(encodePassword());
		daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
		return daoAuthenticationProvider;
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		log.info("AuthenticationManager Has been Inited =================> ");
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("AuthenticationManagerBuilder Has been Created =================> ");
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("HttpSecurity In Web Security =================> ");
		http	
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				//.antMatchers("/oauth/token").permitAll()
				.anyRequest().authenticated();
				//.and()
				//.httpBasic();
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		log.info("WebSecurity Nothing Configured =================> ");
		super.configure(web);
	}
}
