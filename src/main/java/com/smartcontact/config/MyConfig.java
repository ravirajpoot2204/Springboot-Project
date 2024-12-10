package com.smartcontact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class MyConfig {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new CustomUserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		
		return daoAuthenticationProvider;
	}

//configure methods

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
	            .requestMatchers("/admin/**")
	            .hasAuthority("ADMIN")
	            .requestMatchers("/user/**")
	            .hasAuthority("USER")
	            .anyRequest()
	            .permitAll()
	            )
	        .formLogin(formLogin -> formLogin
	        	.loginPage("/login")
	        	.loginProcessingUrl("/do-login")
	        	.defaultSuccessUrl("/user/")
	        //	.failureUrl("/login-failed")
	        	//.loginProcessingUrl(null) used to add custom processing url to add in form
	        	//.defaultSuccessUrl(null) landing page after successfull login
	        	//.failureUrl(null) landing page after unsuccessfull login 
	        	.permitAll());
	    return http.build();
	}
}
