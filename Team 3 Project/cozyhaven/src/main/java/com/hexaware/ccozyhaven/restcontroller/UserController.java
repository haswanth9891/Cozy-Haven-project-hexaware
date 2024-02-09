package com.hexaware.ccozyhaven.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.UserDTO;

import com.hexaware.ccozyhaven.entities.User;

import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cozyhaven-user")
public class UserController {
	 private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;

//		@PostMapping("/addUser")
//		public User addUser(@RequestBody @Valid User user) {
//			
//			 return userService.addUser(user);
//		}

	@PutMapping("/update/{userId}")
	public User updateUser(@PathVariable Long userId, @RequestBody @Valid UserDTO userDTO)
			throws UserNotFoundException {
		LOGGER.info("Received request to update user with ID: {}", userId);

		return userService.updateUser(userId, userDTO);
		 
	}

}
