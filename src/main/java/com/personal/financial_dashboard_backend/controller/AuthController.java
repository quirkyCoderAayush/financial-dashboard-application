package com.personal.financial_dashboard_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.financial_dashboard_backend.dto.AuthResponse;
import com.personal.financial_dashboard_backend.dto.UserDTO;
import com.personal.financial_dashboard_backend.model.AuthRequest;
import com.personal.financial_dashboard_backend.security.JwtTokenProvider;
import com.personal.financial_dashboard_backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;

	public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider,
			AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody AuthRequest registerRequest) {
		try {
			UserDTO registeredUser = userService.registerUser(registerRequest);
			return ResponseEntity.ok(registeredUser);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String token = jwtTokenProvider.generateToken(authentication);
			UserDTO user = userService.getUserByUsername(loginRequest.getUsername());
			AuthResponse response = new AuthResponse(token, user);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error during login: ", e);
			return ResponseEntity.badRequest().body("Invalid login credentials.");
		}
	}
}
