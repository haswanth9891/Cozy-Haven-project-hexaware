package com.hexaware.ccozyhaven.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.LoginDTO;
import com.hexaware.ccozyhaven.dto.UserDTO;

import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.service.IUserService;
import com.hexaware.ccozyhaven.service.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	 private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	
	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public boolean registerUser(@RequestBody UserDTO userDTO) throws DataAlreadyPresentException {
		LOGGER.info("Request Received to register new Customer: "+userDTO);
		return userService.register(userDTO);
	}
	
	@PostMapping("/login")
	public String authenticateAndGetToken(@RequestBody LoginDTO loginDto) {
		String token = null;
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
		if(authentication.isAuthenticated()) {
			token= jwtService.generateToken(loginDto.getUsername());
			if(token != null) {
				LOGGER.info("Token for User: "+token);
			}else {
				LOGGER.warn("Token not generated");
			}
		}else {
			throw new UsernameNotFoundException("Username not found");
		}
		return token;
	}

	@PutMapping("/update/{userId}")
	@PreAuthorize("hasAuthority('USER')")
	public User updateUser(@PathVariable Long userId, @RequestBody @Valid UserDTO userDTO)
			throws UserNotFoundException, AuthorizationException, UnauthorizedAccessException {
		LOGGER.info("Received request to update user with ID: {}", userId);

		return userService.updateUser(userId, userDTO);
		 
	}
	
	 @DeleteMapping("/{userId}")
	    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws UserNotFoundException, AuthorizationException, UnauthorizedAccessException {
	        userService.deleteUser(userId);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

}
