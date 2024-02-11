package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hexaware.ccozyhaven.dto.UserDTO;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;
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
   
    @Autowired
    private PasswordEncoder passwordEncoder;
	
	

    @Test
    void testLogin() {
        // Arrange
        User existingUser = new User();
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setContactNumber("1234567890");
        existingUser.setPassword("$2a$10$abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij"); // Replace with the encoded password
        existingUser.setGender("male");
        existingUser.setUsername("john_doe");
        existingUser.setAddress("123 Main St");
        existingUser.setRole("USER");

        // Save the existing user to the database
        User savedUser = userRepository.save(existingUser);

        // Act
        boolean loginResult = userService.login("john_doe", "your_password");

        // Assert
        assertTrue(loginResult);
    }

	@Test
    void testRegister() throws DataAlreadyPresentException {
        // Create a UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setUserFirstName("John");
        userDTO.setUserLastName("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setContactNumber("1234567890");
        userDTO.setPassword("password");
        userDTO.setGender("male");
        userDTO.setUsername("john_doe");
        userDTO.setAddress("123 Main St");

        // Call the actual method
        Long userId = userService.register(userDTO);

        // Retrieve the user from the database
        User user = userService.findById(userId);

        // Assertions
        assertNotNull(userId);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("1234567890", user.getContactNumber());
        assertTrue(passwordEncoder.matches("password", user.getPassword()));
        assertEquals("male", user.getGender());
        assertEquals("john_doe", user.getUsername());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("USER", user.getRole());
    }
  

	@Test
    public void testUpdateUser() throws UserNotFoundException, AuthorizationException, UnauthorizedAccessException {
        // Arrange
        Long userId = 1L;
        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setEmail("updated-email@example.com");
        updatedUserDTO.setUserFirstName("UpdatedFirstName");
        updatedUserDTO.setUserLastName("UpdatedLastName");
        updatedUserDTO.setContactNumber("9876543210");
        updatedUserDTO.setUsername("updatedUsername");
        updatedUserDTO.setGender("female");
        updatedUserDTO.setAddress("Updated Address");

        User existingUser = new User();
        existingUser.setUserId(userId);
        existingUser.setEmail("old-email@example.com");
        existingUser.setFirstName("OldFirstName");
        existingUser.setLastName("OldLastName");
        existingUser.setContactNumber("1234567890");
        existingUser.setUsername("oldUsername");
        existingUser.setGender("male");
        existingUser.setAddress("Old Address");

        userRepository.save(existingUser);

        // Act
        User updatedUser = userService.updateUser(userId, updatedUserDTO);

        // Assert
        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getUserId());
        assertEquals(updatedUserDTO.getEmail(), updatedUser.getEmail());
        assertEquals(updatedUserDTO.getUserFirstName(), updatedUser.getFirstName());
        assertEquals(updatedUserDTO.getUserLastName(), updatedUser.getLastName());
        assertEquals(updatedUserDTO.getContactNumber(), updatedUser.getContactNumber());
        assertEquals(updatedUserDTO.getUsername(), updatedUser.getUsername());
        assertEquals(updatedUserDTO.getGender(), updatedUser.getGender());
        assertEquals(updatedUserDTO.getAddress(), updatedUser.getAddress());
    }

	@Test
    public void testDeleteUserNotFound() {
        // Arrange
        Long userId = 1L;

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
    }

	 @Test
	    void testFindById() throws UserNotFoundException, AuthorizationException, UnauthorizedAccessException {
	        // Arrange
	        User existingUser = new User();
	        existingUser.setFirstName("John");
	        existingUser.setLastName("Doe");
	        existingUser.setEmail("john.doe@example.com");
	        existingUser.setContactNumber("1234567890");
	        existingUser.setPassword("password");
	        existingUser.setGender("male");
	        existingUser.setUsername("john_doe");
	        existingUser.setAddress("123 Main St");
	        existingUser.setRole("USER");

	        // Save the existing user to the database
	        User savedUser = userRepository.save(existingUser);

	        // Act
	        User foundUser = userService.findById(savedUser.getUserId());

	        // Assert
	        assertNotNull(foundUser);
	        assertEquals(savedUser.getUserId(), foundUser.getUserId());
	        assertEquals(existingUser.getFirstName(), foundUser.getFirstName());
	        assertEquals(existingUser.getLastName(), foundUser.getLastName());
	        assertEquals(existingUser.getEmail(), foundUser.getEmail());
	        assertEquals(existingUser.getContactNumber(), foundUser.getContactNumber());
	        assertEquals(existingUser.getGender(), foundUser.getGender());
	        assertEquals(existingUser.getUsername(), foundUser.getUsername());
	        assertEquals(existingUser.getAddress(), foundUser.getAddress());
	        assertEquals(existingUser.getRole(), foundUser.getRole());
	    }

}
