package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ccozyhaven.dto.UserDTO;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class UserServiceImpTest {
	
	@Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;
    @Test
    void testUpdateUser() throws UserNotFoundException {
        
        User existingUser = new User();
        existingUser.setUserId(1L);
        existingUser.setUserName("john_doe_user");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setContactNumber("1234567890");
        existingUser.setGender("male");
        existingUser.setAddress("123 Main St");
        existingUser.setPassword("john@123");
        userRepository.save(existingUser);

       
        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setEmail("john_updated@example.com");
        updatedUserDTO.setFirstName("JohnUpdated");
        updatedUserDTO.setLastName("DoeUpdated");
        updatedUserDTO.setContactNumber("9876543210");
        updatedUserDTO.setGender("female");
        updatedUserDTO.setAddress("456 New St");
        
        // Providing the existing user ID for updating
        User updatedUser = userService.updateUser(existingUser.getUserId(), updatedUserDTO);

        // Assert: Validate that the user was updated with the new information
        assertNotNull(updatedUser);
        assertEquals(updatedUserDTO.getEmail(), updatedUser.getEmail());
        assertEquals(updatedUserDTO.getFirstName(), updatedUser.getFirstName());
        assertEquals(updatedUserDTO.getLastName(), updatedUser.getLastName());
        assertEquals(updatedUserDTO.getContactNumber(), updatedUser.getContactNumber());
        assertEquals(updatedUserDTO.getGender(), updatedUser.getGender());
        assertEquals(updatedUserDTO.getAddress(), updatedUser.getAddress());

        // i am keeping  password remains unchanged
        assertEquals(existingUser.getPassword(), updatedUser.getPassword());
    }

}
