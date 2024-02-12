package com.hexaware.ccozyhaven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.hexaware.ccozyhaven.dto.UserDTO;

import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;
/*
 * Author: Haswanth
 * 
 * Service description: Provides business logic related to the User entity.
 * It contains methods for registering a new User, logging in, updating details, etc.
 */

@Service
@Transactional
public class UserServiceImp implements IUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

	


	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		LOGGER.info("User is loggin in...");
		return false;
	}

	@Override
	public Long register(UserDTO userDTO) throws DataAlreadyPresentException {

		
		User user = new User();
		user.setFirstName(userDTO.getUserFirstName());
		user.setLastName(userDTO.getUserLastName());
		user.setEmail(userDTO.getEmail());
		user.setContactNumber(userDTO.getContactNumber());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setGender(userDTO.getGender());
		user.setUsername(userDTO.getUsername());
		user.setAddress(userDTO.getAddress());
		user.setRole("USER");
		LOGGER.info("Registering Customer: " + user);
		User addedUser = userRepository.save(user);

		if (addedUser != null) {
			LOGGER.info("Registerd Customer: " + addedUser);
			return user.getUserId();
		}
		LOGGER.error("Customer not registered");
		return null;
	}

	@Override
	
	public User updateUser(Long userId, UserDTO updatedUserDTO) throws UserNotFoundException, AuthorizationException, UnauthorizedAccessException {
		LOGGER.info("Updating user with ID {}", userId);
		

			User existingUser = userRepository.findById(userId)
					.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

			existingUser.setEmail(updatedUserDTO.getEmail());

			existingUser.setFirstName(updatedUserDTO.getUserFirstName());

			existingUser.setLastName(updatedUserDTO.getUserLastName());
			existingUser.setContactNumber(updatedUserDTO.getContactNumber());
			existingUser.setUsername(updatedUserDTO.getUsername());
			existingUser.setPassword(updatedUserDTO.getPassword());
			existingUser.setGender(updatedUserDTO.getGender());
			existingUser.setAddress(updatedUserDTO.getAddress());

			LOGGER.info("User updated successfully");
			return userRepository.save(existingUser);
		
		}

	
	
	@Override
	
	public String deleteUser(Long userId) throws UserNotFoundException, AuthorizationException, UnauthorizedAccessException {
	    LOGGER.info("Deleting user with ID {}", userId);
	    try {
	        // Delete the user
	        userRepository.deleteById(userId);

	        LOGGER.info("User with ID {} deleted successfully", userId);
	        return "User with ID " + userId + " deleted successfully";
	    
	    } catch (Exception e) {
	        LOGGER.error("Error deleting user with ID {}: {}", userId, e.getMessage());
	        return "Error deleting user with ID " + userId + ": " + e.getMessage();
	    }

	   
	}

	@Override
    public User findById(Long userId) { 
        return userRepository.findById(userId).orElse(null);
    }

	
}
