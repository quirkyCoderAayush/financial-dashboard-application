package com.personal.financial_dashboard_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.personal.financial_dashboard_backend.model.User;
import com.personal.financial_dashboard_backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Attempting to load user by username: " + username);
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		System.out.println("Attempting to load user by username: " + username);
		return UserDetailsImpl.build(user);
	}

//	@Transactional
//	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
//		User user = userRepository.findByEmail(email)
//				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//
//		return UserDetailsImpl.build(user);
//	}

}
