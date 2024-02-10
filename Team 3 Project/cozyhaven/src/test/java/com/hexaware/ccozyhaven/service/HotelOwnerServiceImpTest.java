package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ccozyhaven.dto.HotelDTO;
import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;
import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.HotelOwner;

import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;

import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.HotelRepository;

@SpringBootTest
class HotelOwnerServiceImpTest {

	@Autowired
	private HotelOwnerServiceImp hotelOwnerService;

	@Autowired
	private HotelServiceImp hotelService;

	@Autowired
	private HotelOwnerRepository hotelOwnerRepository;

	@Autowired
	private HotelRepository hotelRepository;

	@Test
	public void testAddHotelOwnerWithHotel_Success() {

		HotelOwnerDTO hotelOwner = new HotelOwnerDTO();
		hotelOwner.setHotelOwnerName("Meena");
		hotelOwner.setPassword("securePassword");
		hotelOwner.setEmail("meean@example.com");
		hotelOwner.setGender("male");
		hotelOwner.setAddress("123 Main Street, City");

		HotelDTO hotel = new HotelDTO();
		hotel.setHotelName("Rest Inn");
		hotel.setLocation("Mumbai");
		hotel.setHasDining(true);
		hotel.setHasParking(false);
		hotel.setHasFreeWiFi(true);
		hotel.setHasRoomService(true);
		hotel.setHasSwimmingPool(false);
		hotel.setHasFitnessCenter(true);

		hotelOwner.setHotelDTO(hotel);
		

		hotelOwnerService.addHotelOwnerWithHotel(hotelOwner);

		HotelOwner savedHotelOwner = hotelOwnerRepository.findById(hotelOwner.getHotelOwnerId()).orElse(null);
		assertNotNull(savedHotelOwner);

		Hotel savedHotel = hotelRepository.findById(hotel.getHotelId()).orElse(null);
		assertNotNull(savedHotel);

		assertEquals(hotelOwner.getHotelOwnerName(), savedHotelOwner.getHotelOwnerName());
		assertEquals(hotelOwner.getEmail(), savedHotelOwner.getEmail());

		assertEquals(hotel.getHotelName(), savedHotel.getHotelName());
		assertEquals(hotel.getLocation(), savedHotel.getLocation());

	}

	@Test
	void testUpdateHotelOwner() throws HotelOwnerNotFoundException {

	    // Prepare existing hotel owner data
	    Long hotelOwnerId = 1L;
	    HotelOwnerDTO existingHotelOwnerDTO = new HotelOwnerDTO();
	    existingHotelOwnerDTO.setHotelOwnerId(hotelOwnerId);
	    existingHotelOwnerDTO.setHotelOwnerName("Madhu");
	    existingHotelOwnerDTO.setPassword("securePassword");
	    existingHotelOwnerDTO.setEmail("john.doe@example.com");
	    existingHotelOwnerDTO.setGender("male");
	    existingHotelOwnerDTO.setAddress("123 Main Street, City");

	    HotelDTO existingHotelDTO = new HotelDTO();
	    existingHotelDTO.setHotelName("Luxury Inn");
	    existingHotelDTO.setLocation("Downtown");
	    existingHotelDTO.setHasDining(true);
	    existingHotelDTO.setHasParking(true);
	    existingHotelDTO.setHasFreeWiFi(true);
	    existingHotelDTO.setHasRoomService(true);
	    existingHotelDTO.setHasSwimmingPool(false);
	    existingHotelDTO.setHasFitnessCenter(true);

	    existingHotelOwnerDTO.setHotelDTO(existingHotelDTO);

	    // Update hotel owner using the service
	    hotelOwnerService.updateHotelOwnerWithHotel(hotelOwnerId, existingHotelOwnerDTO);

	    // Retrieve updated hotel owner from the repository
	    HotelOwner updatedHotelOwner = hotelOwnerRepository.findById(hotelOwnerId)
	            .orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

	    // Assertions
	    assertEquals(existingHotelOwnerDTO.getHotelOwnerName(), updatedHotelOwner.getHotelOwnerName());
	    assertEquals(existingHotelOwnerDTO.getEmail(), updatedHotelOwner.getEmail());
	    assertEquals(existingHotelOwnerDTO.getGender(), updatedHotelOwner.getGender());
	    assertEquals(existingHotelOwnerDTO.getAddress(), updatedHotelOwner.getAddress());

	    // Hotel assertions
	    Hotel updatedHotel = updatedHotelOwner.getHotel();
	    assertEquals(existingHotelDTO.getHotelName(), updatedHotel.getHotelName());
	    assertEquals(existingHotelDTO.getLocation(), updatedHotel.getLocation());
	    assertEquals(existingHotelDTO.isHasDining(), updatedHotel.isHasDining());
	    assertEquals(existingHotelDTO.isHasParking(), updatedHotel.isHasParking());
	    assertEquals(existingHotelDTO.isHasFreeWiFi(), updatedHotel.isHasFreeWiFi());
	    assertEquals(existingHotelDTO.isHasRoomService(), updatedHotel.isHasRoomService());
	    assertEquals(existingHotelDTO.isHasSwimmingPool(), updatedHotel.isHasSwimmingPool());
	    assertEquals(existingHotelDTO.isHasFitnessCenter(), updatedHotel.isHasFitnessCenter());
	}


	@Test
	public void testDeleteHotelOwner_Success() throws HotelOwnerNotFoundException {
		// Arrange
		HotelOwner hotelOwner = new HotelOwner();
		hotelOwner.setHotelOwnerName("Ishwar");
		hotelOwner.setPassword("ishwari123");
		hotelOwner.setEmail("ishwar456@example.com");
		hotelOwner.setGender("female");
		hotelOwner.setAddress("123 Main Street, City");

		Hotel hotel = new Hotel();
		hotel.setHotelName("Madhu1 Inn");
		hotel.setLocation("Mumbai");
		hotel.setHasDining(true);
		hotel.setHasParking(false);
		hotel.setHasFreeWiFi(true);
		hotel.setHasRoomService(true);
		hotel.setHasSwimmingPool(true);
		hotel.setHasFitnessCenter(false);

		hotelOwner.setHotel(hotel);
		hotel.setHotelOwner(hotelOwner);

		hotelOwnerRepository.save(hotelOwner);

		Long hotelOwnerId = hotelOwner.getHotelOwnerId();

		hotelOwnerService.deleteHotelOwner(hotelOwnerId);

		assertFalse(hotelOwnerRepository.existsById(hotelOwnerId));
	}

	@Test
	public void testDeleteHotelOwner_HotelOwnerNotFound() {

		Long nonExistentHotelOwnerId = 999L;

		assertThrows(HotelOwnerNotFoundException.class,
				() -> hotelOwnerService.deleteHotelOwner(nonExistentHotelOwnerId));
	}

}
