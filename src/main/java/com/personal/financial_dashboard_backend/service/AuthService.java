package com.personal.financial_dashboard_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.personal.financial_dashboard_backend.dto.JwtResponse;
import com.personal.financial_dashboard_backend.dto.LoginRequest;
import com.personal.financial_dashboard_backend.dto.SignupRequest;
import com.personal.financial_dashboard_backend.model.User;
import com.personal.financial_dashboard_backend.repository.UserRepository;
import com.personal.financial_dashboard_backend.security.JwtTokenProvider;
import com.personal.financial_dashboard_backend.security.UserDetailsImpl;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
			PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public JwtResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenProvider.generateToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail());
	}

	public User registerUser(SignupRequest signupRequest) {
		// Check if username exists
		if (userRepository.existsByUsername(signupRequest.getUsername())) {
			throw new RuntimeException("Username is already taken!");
		}

		// Check if email exists
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			throw new RuntimeException("Email is already in use!");
		}

		// Create new user
		User user = new User();
		user.setUsername(signupRequest.getUsername());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		user.setFirstName(signupRequest.getFirstName());
		user.setLastName(signupRequest.getLastName());

		return userRepository.save(user);
	}

}
