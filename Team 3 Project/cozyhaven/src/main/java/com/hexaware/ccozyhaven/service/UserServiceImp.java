package com.hexaware.ccozyhaven.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.hexaware.ccozyhaven.dto.UserDTO;



import com.hexaware.ccozyhaven.entities.User;

import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImp implements IUserService {

	

	@Autowired
	private UserRepository userRepository;

//	@Override
//	public User addUser(User user) {
//		
//		return userRepository.save(user);
//	}

	@Override
	public User updateUser(Long userId, UserDTO updatedUserDTO) throws UserNotFoundException {

		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

		existingUser.setEmail(updatedUserDTO.getEmail());

		existingUser.setFirstName(updatedUserDTO.getFirstName());

		existingUser.setLastName(updatedUserDTO.getLastName());
		existingUser.setContactNumber(updatedUserDTO.getContactNumber());
		existingUser.setGender(updatedUserDTO.getGender());
		existingUser.setAddress(updatedUserDTO.getAddress());

		return userRepository.save(existingUser);

	}

	
	

	

}
