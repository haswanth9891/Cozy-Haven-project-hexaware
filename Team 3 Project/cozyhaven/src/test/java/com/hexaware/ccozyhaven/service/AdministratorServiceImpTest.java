package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

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
		User user = new User();
        user.setUserId(1L);
        userRepository.save(user);

        assertDoesNotThrow(() -> administratorService.deleteUserAccount(1L));

        Optional<User> deletedUser = userRepository.findById(1L);
        assert(!deletedUser.isPresent());
	}

	@Test
	void testDeleteHotelOwnerAccount() {
		HotelOwner hotelOwner = new HotelOwner();
        hotelOwner.setHotelOwnerId(1L);
        hotelOwnerRepository.save(hotelOwner);

        
        assertDoesNotThrow(() -> administratorService.deleteHotelOwnerAccount(1L));

       
        Optional<HotelOwner> deletedHotelOwner = hotelOwnerRepository.findById(1L);
        assertFalse(deletedHotelOwner.isPresent());

	}

	@Test
	void testViewAllUser() {
		User user1 = new User();
        user1.setUserId(1L);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUserId(2L);
        userRepository.save(user2);

        List<User> userList = administratorService.viewAllUser();
        assertEquals(2, userList.size());
	}

	@Test
	void testViewAllHotelOwner() {
		 HotelOwner hotelOwner1 = new HotelOwner();
	        hotelOwner1.setHotelOwnerId(1L);
	        hotelOwnerRepository.save(hotelOwner1);

	        HotelOwner hotelOwner2 = new HotelOwner();
	        hotelOwner2.setHotelOwnerId(2L);
	        hotelOwnerRepository.save(hotelOwner2);

	        List<HotelOwner> hotelOwnerList = administratorService.viewAllHotelOwner();
	        assertEquals(2, hotelOwnerList.size());
	}

	@Test
	void testManageRoomReservation() {
		 Reservation reservation = new Reservation();
	        reservation.setReservationId(1L);
	        reservation.setReservationStatus("BOOKED");
	        reservationRepository.save(reservation);

	        assertDoesNotThrow(() -> administratorService.manageRoomReservation(1L, "CANCELLED"));

	        Optional<Reservation> deletedReservation = reservationRepository.findById(1L);
	        assert(!deletedReservation.isPresent());
	}

}
