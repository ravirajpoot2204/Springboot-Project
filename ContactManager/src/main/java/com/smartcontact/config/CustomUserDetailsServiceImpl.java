package com.smartcontact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartcontact.dao.UserRepo;
import com.smartcontact.entities.User;

public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.getUserByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("Could not found the user!! : " + username);
		}
		CustomUserDetails userDetails = new CustomUserDetails(user);
		return userDetails;
	}

}
