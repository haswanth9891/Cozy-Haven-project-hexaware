package com.hexaware.ccozyhaven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.config.UserInfoUserDetails;
import com.hexaware.ccozyhaven.dto.UserDTO;

import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImp implements IUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

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
	public boolean register(UserDTO userDTO) throws DataAlreadyPresentException {

		User UserByEmail = getUserByEmail(userDTO.getEmail());
		if (userDTO.getEmail() == UserByEmail.getEmail()) {
			LOGGER.warn("User is trying to enter DUPLICATE data while registering");
			throw new DataAlreadyPresentException("PhoneNumber or Email already taken...Trying Logging in..!");
		}
		User user = new User();
		user.setFirstName(userDTO.getUserFirstName());
		user.setLastName(userDTO.getUserLastName());
		user.setEmail(userDTO.getEmail());
		user.setContactNumber(userDTO.getContactNumber());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setGender(userDTO.getGender());

		user.setAddress(userDTO.getAddress());
		LOGGER.info("Registering Customer: " + user);
		User addedUser = userRepository.save(user);

		if (addedUser != null) {
			LOGGER.info("Registerd Customer: " + addedUser);
			return true;
		}
		LOGGER.error("Customer not registered");
		return false;
	}

	@Override
	@PreAuthorize("#userId == principal.id")
	public User updateUser(Long userId, UserDTO updatedUserDTO) throws UserNotFoundException, AuthorizationException, UnauthorizedAccessException {
		LOGGER.info("Updating user with ID {}", userId);
		// Retrieve the authenticated user details from the security context
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Check if the user is authenticated
		if (authentication != null && authentication.isAuthenticated()) {
			UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();

			// Check if the authenticated user ID matches the specified userId
			if (!userDetails.getUsername().equals(updatedUserDTO.getEmail())) {
				throw new AuthorizationException("You are not authorized to update this user.");
			}

			// Retrieve the user to be updated

			User existingUser = userRepository.findById(userId)
					.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

			existingUser.setEmail(updatedUserDTO.getEmail());

			existingUser.setFirstName(updatedUserDTO.getFirstName());

			existingUser.setLastName(updatedUserDTO.getLastName());
			existingUser.setContactNumber(updatedUserDTO.getContactNumber());
			existingUser.setGender(updatedUserDTO.getGender());
			existingUser.setAddress(updatedUserDTO.getAddress());

			LOGGER.info("User updated successfully");
			return userRepository.save(existingUser);
		} else {
			throw new UnauthorizedAccessException("User not authenticated or invalid JWT token.");
		}

	}
	
	@Override
	@PreAuthorize("#userId == principal.id") // Check if the authenticated user is the same as the user to be deleted
	public void deleteUser(Long userId) throws UserNotFoundException, AuthorizationException, UnauthorizedAccessException {
	    LOGGER.info("Deleting user with ID {}", userId);

	    // Retrieve the authenticated user details from the security context
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    // Check if the user is authenticated
	    if (authentication != null && authentication.isAuthenticated()) {
	        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();

	        // Check if the authenticated user ID matches the specified userId
	        if (!userDetails.getUsername().equals(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId)).getEmail())) {
	            throw new AuthorizationException("You are not authorized to delete this user.");
	        }

	        // Delete the user
	        userRepository.deleteById(userId);

	        LOGGER.info("User with ID {} deleted successfully", userId);
	    } else {
	        throw new UnauthorizedAccessException("User not authenticated or invalid JWT token.");
	    }
	}

	public User getUserByEmail(String email) {
		LOGGER.info("Finding " + email + " in database");
		return userRepository.findByEmail(email).orElse(null);
	}

}
