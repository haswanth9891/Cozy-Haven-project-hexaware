package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

		HotelOwner hotelOwner = new HotelOwner();
		hotelOwner.setHotelOwnerName("Meena");
		hotelOwner.setPassword("securePassword");
		hotelOwner.setEmail("meean@example.com");
		hotelOwner.setGender("male");
		hotelOwner.setAddress("123 Main Street, City");

		Hotel hotel = new Hotel();
		hotel.setHotelName("Rest Inn");
		hotel.setLocation("Mumbai");
		hotel.setHasDining(true);
		hotel.setHasParking(false);
		hotel.setHasFreeWiFi(true);
		hotel.setHasRoomService(true);
		hotel.setHasSwimmingPool(false);
		hotel.setHasFitnessCenter(true);

		hotelOwner.setHotel(hotel);
		hotel.setHotelOwner(hotelOwner);

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

		HotelOwner existingHotelOwner = new HotelOwner();
		Long hotelOwnerId = 1L;
		existingHotelOwner.setHotelOwnerName("Madhu");
		existingHotelOwner.setPassword("securePassword");
		existingHotelOwner.setEmail("john.doe@example.com");
		existingHotelOwner.setGender("male");
		existingHotelOwner.setAddress("123 Main Street, City");

		Hotel hotel = new Hotel();
		hotel.setHotelName("Luxury Inn");
		hotel.setLocation("Downtown");
		hotel.setHasDining(true);
		hotel.setHasParking(true);
		hotel.setHasFreeWiFi(true);
		hotel.setHasRoomService(true);
		hotel.setHasSwimmingPool(false);
		hotel.setHasFitnessCenter(true);

		existingHotelOwner.setHotel(hotel);


		HotelOwner updatedHotelOwner = hotelOwnerService.updateHotelOwner(hotelOwnerId, existingHotelOwner);

		
		assertEquals(existingHotelOwner.getHotelOwnerName(), updatedHotelOwner.getHotelOwnerName());
		assertEquals(existingHotelOwner.getPassword(), updatedHotelOwner.getPassword());
		assertEquals(existingHotelOwner.getEmail(), updatedHotelOwner.getEmail());
		assertEquals(existingHotelOwner.getGender(), updatedHotelOwner.getGender());
		assertEquals(existingHotelOwner.getAddress(), updatedHotelOwner.getAddress());

		
		assertEquals(existingHotelOwner.getHotel().getHotelName(), updatedHotelOwner.getHotel().getHotelName());
		assertEquals(existingHotelOwner.getHotel().getLocation(), updatedHotelOwner.getHotel().getLocation());
		assertEquals(existingHotelOwner.getHotel().isHasDining(), updatedHotelOwner.getHotel().isHasDining());
		assertEquals(existingHotelOwner.getHotel().isHasParking(), updatedHotelOwner.getHotel().isHasParking());
		assertEquals(existingHotelOwner.getHotel().isHasFreeWiFi(), updatedHotelOwner.getHotel().isHasFreeWiFi());
		assertEquals(existingHotelOwner.getHotel().isHasRoomService(), updatedHotelOwner.getHotel().isHasRoomService());
		assertEquals(existingHotelOwner.getHotel().isHasSwimmingPool(),
				updatedHotelOwner.getHotel().isHasSwimmingPool());
		assertEquals(existingHotelOwner.getHotel().isHasFitnessCenter(),
				updatedHotelOwner.getHotel().isHasFitnessCenter());

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
