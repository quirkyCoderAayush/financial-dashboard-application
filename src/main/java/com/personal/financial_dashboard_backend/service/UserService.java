package com.personal.financial_dashboard_backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.personal.financial_dashboard_backend.dto.UserDTO;
import com.personal.financial_dashboard_backend.model.AuthRequest;
import com.personal.financial_dashboard_backend.model.User;
import com.personal.financial_dashboard_backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public UserDTO registerUser(AuthRequest authRequest) {
		if (userRepository.existsByUsername(authRequest.getUsername())) {
			throw new RuntimeException("Username already in use");
		}
		if (userRepository.existsByEmail(authRequest.getEmail())) {
			throw new RuntimeException("Email already in use");
		}

		User user = new User();
		user.setUsername(authRequest.getUsername());
		user.setEmail(authRequest.getEmail());
		user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
		user.setFirstName(authRequest.getFirstName());
		user.setLastName(authRequest.getLastName());
		User savedUser = userRepository.save(user);
		return convertToDTO(savedUser);
	}

	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public UserDTO getUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found with username: " + username));
		return convertToDTO(user);
	}

	public UserDTO getUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
		return convertToDTO(user);
	}

	@Transactional
	public UserDTO createUser(UserDTO userDTO) {
		if (userRepository.existsByUsername(userDTO.getUsername())) {
			throw new RuntimeException("Username already in use");
		}
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new RuntimeException("Email already in use");
		}

		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());

		User savedUser = userRepository.save(user);
		return convertToDTO(savedUser);
	}

	@Transactional
	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());

		if (Optional.ofNullable(userDTO.getPassword()).isPresent()) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}

		User updatedUser = userRepository.save(user);
		return convertToDTO(updatedUser);
	}

	@Transactional
	public void deleteUser(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new RuntimeException("User not found with ID: " + userId);
		}
		userRepository.deleteById(userId);
	}

	private UserDTO convertToDTO(User user) {
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		return userDto;
	}

}
