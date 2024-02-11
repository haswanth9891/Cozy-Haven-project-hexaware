package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ccozyhaven.dto.HotelDTO;
import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.HotelRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class HotelOwnerServiceImpTest {
	 
	 @Autowired
	    private IHotelOwnerService hotelOwnerService;

	    @Autowired
	    private HotelOwnerRepository hotelOwnerRepository;

	    @Autowired
	    private HotelRepository hotelRepository;
	

	@Test
	void testLogin() {
	
	}

	@Test
    void testRegisterHotelOwner() throws DataAlreadyPresentException {
        // Arrange
        HotelOwnerDTO hotelOwnerDTO = new HotelOwnerDTO();
        hotelOwnerDTO.setHotelOwnerName("John Doe");
        hotelOwnerDTO.setEmail("john.doe@example.com");
        hotelOwnerDTO.setUsername("john_doe_owner");
        hotelOwnerDTO.setPassword("john@123");
        hotelOwnerDTO.setGender("male");
        hotelOwnerDTO.setAddress("123 Main St");

        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelName("Hotel ABC");
        hotelDTO.setLocation("Location XYZ");
        hotelDTO.setHasDining(true);
        hotelDTO.setHasParking(true);
        hotelDTO.setHasFreeWiFi(true);
        hotelDTO.setHasRoomService(true);
        hotelDTO.setHasSwimmingPool(true);
        hotelDTO.setHasFitnessCenter(true);

        hotelOwnerDTO.setHotelDTO(hotelDTO);

        // Act
        Long hotelOwnerId = hotelOwnerService.registerHotelOwner(hotelOwnerDTO);

        // Assert
        assertNotNull(hotelOwnerId);
        HotelOwner savedHotelOwner = hotelOwnerRepository.findById(hotelOwnerId).orElse(null);
        assertNotNull(savedHotelOwner);
        assertEquals("John Doe", savedHotelOwner.getHotelOwnerName());
        assertNotNull(savedHotelOwner.getHotel());
        assertEquals("Hotel ABC", savedHotelOwner.getHotel().getHotelName());
    }

	@Test
    void testUpdateHotelOwnerWithHotel() throws HotelOwnerNotFoundException, AuthorizationException, UnauthorizedAccessException, DataAlreadyPresentException {
        // Arrange
        HotelOwnerDTO hotelOwnerDTO = new HotelOwnerDTO();
        hotelOwnerDTO.setHotelOwnerName("John Doe");
        hotelOwnerDTO.setEmail("john.doe@example.com");
        hotelOwnerDTO.setUsername("john_doe_owner");
        hotelOwnerDTO.setPassword("john@123");
        hotelOwnerDTO.setGender("male");
        hotelOwnerDTO.setAddress("123 Main St");

        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelName("Hotel ABC");
        hotelDTO.setLocation("Location XYZ");
        hotelDTO.setHasDining(true);
        hotelDTO.setHasParking(true);
        hotelDTO.setHasFreeWiFi(true);
        hotelDTO.setHasRoomService(true);
        hotelDTO.setHasSwimmingPool(true);
        hotelDTO.setHasFitnessCenter(true);

        hotelOwnerDTO.setHotelDTO(hotelDTO);

        Long hotelOwnerId = hotelOwnerService.registerHotelOwner(hotelOwnerDTO);

        HotelOwnerDTO updatedHotelOwnerDTO = new HotelOwnerDTO();
        updatedHotelOwnerDTO.setHotelOwnerName("Updated Name");
        updatedHotelOwnerDTO.setEmail("updated.email@example.com");
        updatedHotelOwnerDTO.setUsername("updated_owner");
        updatedHotelOwnerDTO.setGender("female");
        updatedHotelOwnerDTO.setAddress("Updated Address");

        HotelDTO updatedHotelDTO = new HotelDTO();
        updatedHotelDTO.setHotelName("Updated Hotel");
        updatedHotelDTO.setLocation("Updated Location");
        updatedHotelDTO.setHasDining(false);
        updatedHotelDTO.setHasParking(false);
        updatedHotelDTO.setHasFreeWiFi(false);
        updatedHotelDTO.setHasRoomService(false);
        updatedHotelDTO.setHasSwimmingPool(false);
        updatedHotelDTO.setHasFitnessCenter(false);

        updatedHotelOwnerDTO.setHotelDTO(updatedHotelDTO);

        // Act
        hotelOwnerService.updateHotelOwnerWithHotel(hotelOwnerId, updatedHotelOwnerDTO);

        // Assert
        HotelOwner updatedHotelOwner = hotelOwnerRepository.findById(hotelOwnerId).orElse(null);
        assertNotNull(updatedHotelOwner);
        assertEquals("Updated Name", updatedHotelOwner.getHotelOwnerName());
        assertEquals("updated.email@example.com", updatedHotelOwner.getEmail());
        assertEquals("updated_owner", updatedHotelOwner.getUsername());
        assertEquals("female", updatedHotelOwner.getGender());
        assertEquals("Updated Address", updatedHotelOwner.getAddress());

        assertNotNull(updatedHotelOwner.getHotel());
        assertEquals("Updated Hotel", updatedHotelOwner.getHotel().getHotelName());
        assertEquals("Updated Location", updatedHotelOwner.getHotel().getLocation());
        assertFalse(updatedHotelOwner.getHotel().isHasDining());
        assertFalse(updatedHotelOwner.getHotel().isHasParking());
        assertFalse(updatedHotelOwner.getHotel().isHasFreeWiFi());
        assertFalse(updatedHotelOwner.getHotel().isHasRoomService());
        assertFalse(updatedHotelOwner.getHotel().isHasSwimmingPool());
        assertFalse(updatedHotelOwner.getHotel().isHasFitnessCenter());
    }

	@Test
    void testDeleteHotelOwnerById() throws HotelOwnerNotFoundException, AuthorizationException, UnauthorizedAccessException, DataAlreadyPresentException {
        // Arrange
        HotelOwnerDTO hotelOwnerDTO = new HotelOwnerDTO();
        hotelOwnerDTO.setHotelOwnerName("John Doe");
        hotelOwnerDTO.setEmail("john.doe@example.com");
        hotelOwnerDTO.setUsername("john_doe_owner");
        hotelOwnerDTO.setPassword("john@123");
        hotelOwnerDTO.setGender("male");
        hotelOwnerDTO.setAddress("123 Main St");

        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelName("Hotel ABC");
        hotelDTO.setLocation("Location XYZ");
        hotelDTO.setHasDining(true);
        hotelDTO.setHasParking(true);
        hotelDTO.setHasFreeWiFi(true);
        hotelDTO.setHasRoomService(true);
        hotelDTO.setHasSwimmingPool(true);
        hotelDTO.setHasFitnessCenter(true);

        hotelOwnerDTO.setHotelDTO(hotelDTO);

        Long hotelOwnerId = hotelOwnerService.registerHotelOwner(hotelOwnerDTO);

        // Act
        String result = hotelOwnerService.deleteHotelOwner(hotelOwnerId);

        // Assert
        assertEquals("Hotel Owner with ID: " + hotelOwnerId + " deleted successfully", result);
        assertFalse(hotelOwnerRepository.existsById(hotelOwnerId));
        assertNull(hotelRepository.findById(hotelOwnerId));
    }
}
