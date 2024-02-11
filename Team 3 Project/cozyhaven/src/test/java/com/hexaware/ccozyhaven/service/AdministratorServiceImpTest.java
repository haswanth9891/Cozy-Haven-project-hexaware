package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hexaware.ccozyhaven.dto.AdministratorDTO;
import com.hexaware.ccozyhaven.entities.Administrator;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.repository.AdministratorRepository;
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class AdministratorServiceImpTest {

    @Autowired
    private IAdministratorService administratorService;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelOwnerRepository hotelOwnerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testLogin() throws DataAlreadyPresentException, UserNotFoundException {
        // Arrange
        AdministratorDTO administratorDTO = new AdministratorDTO();
        administratorDTO.setAdminFirstName("Javeed");
        administratorDTO.setAdminLastName("Mohammed");
        administratorDTO.setUsername("javeed_513");
        administratorDTO.setPassword("superTrainer123");
        administratorDTO.setEmail("javeed.md@gmail.com");

        // Register the administrator
        administratorService.register(administratorDTO);

        // Act
        String result = administratorService.login("javeed_513", "superTrainer123");

        // Assert
        assertNotNull(result);
        assertEquals("ADMIN", result);
    }

    @Test
    void testRegister() throws DataAlreadyPresentException, UserNotFoundException {
        // Arrange
        AdministratorDTO administratorDTO = new AdministratorDTO();
        administratorDTO.setAdminFirstName("Javeed");
        administratorDTO.setAdminLastName("Mohammed");
        administratorDTO.setUsername("javeed_513");
        administratorDTO.setPassword("superTrainer123");
        administratorDTO.setEmail("javeed.md@gmail.com");

        // Act
        Long adminId = administratorService.register(administratorDTO);

        // Assert
        assertNotNull(adminId);

        // Verify that the administrator is stored in the database
        assertNotNull(administratorRepository.findById(adminId).orElse(null));

        // Verify that the password is encoded
        Administrator storedAdmin = administratorRepository.findById(adminId).orElse(null);
        assertNotNull(storedAdmin);
        assertTrue(passwordEncoder.matches("superTrainer123", storedAdmin.getPassword()));
    }

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
