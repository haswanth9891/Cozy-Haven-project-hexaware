package com.hexaware.ccozyhaven.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.AuthRequest;
import com.hexaware.ccozyhaven.dto.UserDTO;

import com.hexaware.ccozyhaven.entities.User;

import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;

import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

import com.hexaware.ccozyhaven.service.IUserService;
import com.hexaware.ccozyhaven.service.JwtService;

import jakarta.validation.Valid;
/*
 * Author: Haswanth
 * 
 * Controller description: Handles HTTP requests related to the User entity.
 * It contains methods for registering a new User, logging in, updating details, etc.
 */


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/user")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	private final IUserService userService;

	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public String registerUser(@RequestBody @Valid UserDTO userDTO) throws DataAlreadyPresentException {
		LOGGER.info("Request Received to register new Customer: {}", userDTO);
		long userId = userService.register(userDTO);

		if (userId != 0) {
			return "User added successfully ";
		} else {
			return "failed to add user ";
		}
	}

	@PostMapping("/login")
	public String authenticateAndGetTokent(@RequestBody AuthRequest authRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		String token = null;

		if (authentication.isAuthenticated()) {

			// call generate token method from jwtService class

			token = jwtService.generateToken(authRequest.getUsername());

			LOGGER.info("Tokent : {}", token);

		} else {

			LOGGER.info("invalid");

			throw new UsernameNotFoundException("UserName or Password in Invalid / Invalid Request");

		}

		return token;

	}

	@PutMapping("/update/{userId}")
	@PreAuthorize("hasAuthority('USER')")
	public User updateUser(@PathVariable Long userId, @RequestBody @Valid UserDTO userDTO)
			throws UserNotFoundException {
		LOGGER.info("Received request to update user with ID: {}", userId);

		return userService.updateUser(userId, userDTO);

	}

	@DeleteMapping("/delete/{userId}")
	@PreAuthorize("hasAuthority('USER')")
	public String deleteUser(@PathVariable Long userId) throws UserNotFoundException {
		return userService.deleteUser(userId);

	}

}
