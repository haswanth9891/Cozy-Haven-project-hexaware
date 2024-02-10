package com.hexaware.ccozyhaven.service;



import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.dto.UserDTO;

import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

@Repository
public interface IUserService {

//	// User registration
//    User registerUser(UserDTO userDTO);
//
//    // User login
//    boolean loginUser(String username, String password);
	// add the user
	// public User addUser(User user);

	User updateUser(Long userId, UserDTO updatedUserDTO) throws UserNotFoundException;

	boolean login(String username, String password);

	boolean register(UserDTO userDTO) throws DataAlreadyPresentException;
	
}

	