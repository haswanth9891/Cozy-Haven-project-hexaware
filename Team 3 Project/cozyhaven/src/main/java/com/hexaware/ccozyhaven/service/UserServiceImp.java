package com.hexaware.ccozyhaven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.UserDTO;

import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImp implements IUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

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
		user.setPassword(userDTO.getPassword());
		user.setGender(userDTO.getGender());

		user.setAddress(userDTO.getAddress());

		user.setRole("Regular");
		LOGGER.info("Registerd User: " + user);
		User addedUser = userRepository.save(user);

		if (addedUser != null) {
			LOGGER.info("Registerd User: " + addedUser);
			return true;
		}
		LOGGER.error("User not registered");
		return false;
	}

	@Override
	public User updateUser(Long userId, UserDTO updatedUserDTO) throws UserNotFoundException {
		LOGGER.info("Updating user with ID {}", userId);
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

	}
	


	
	public User getUserByEmail(String email) {
		LOGGER.info("Finding "+email+" in database");
		return userRepository.findByEmail(email).orElse(null);
	}

}
