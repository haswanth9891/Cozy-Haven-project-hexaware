package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

@SpringBootTest
class AdministratorServiceImpTest {

	@Autowired
	private AdministratorServiceImp administratorService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HotelOwnerRepository hotelOwnerRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Test
	void testDeleteUserAccount() {

		assertDoesNotThrow(() -> administratorService.deleteUserAccount(1L));

		Optional<User> deletedUser = userRepository.findById(1L);
		assert (!deletedUser.isPresent());
	}

	@Test
	void testDeleteHotelOwnerAccount() {

		assertDoesNotThrow(() -> administratorService.deleteHotelOwnerAccount(2L));

		Optional<HotelOwner> deletedHotelOwner = hotelOwnerRepository.findById(2L);
		assertFalse(deletedHotelOwner.isPresent());

	}

	@Test
	void testViewAllUser() {

		List<User> userList = administratorService.viewAllUser();
		assertEquals(1, userList.size());
	}

	@Test
	void testViewAllHotelOwner() {

		List<HotelOwner> hotelOwnerList = administratorService.viewAllHotelOwner();
		assertEquals(6, hotelOwnerList.size());
	}

	@Test
	void testManageRoomReservation() {

		assertDoesNotThrow(() -> administratorService.manageRoomReservation(3L, "CANCELLED"));

		Optional<Reservation> deletedReservation = reservationRepository.findById(3L);
		assert (!deletedReservation.isPresent());
	}

}
