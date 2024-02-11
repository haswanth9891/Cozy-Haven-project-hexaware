package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ccozyhaven.dto.BookedRoomDTO;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.InconsistentHotelException;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotAvailableException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.RoomRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class ReservationServiceImpTest {

	@Autowired
	IReservationService reservationService;

	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

	@Test
	void testViewReservationByHotelId() {

		Long hotelId = 3L;
		List<Reservation> reservations = reservationService.viewReservationByHotelId(hotelId);
		assertNotNull(reservations);
		assertFalse(reservations.isEmpty());
		for (Reservation reservation : reservations) {
			assertEquals(hotelId, reservation.getHotel().getHotelId());
		}
	}

	void testViewValidReservationByHotelId() {

		Long hotelId = 1L;
		List<Reservation> reservations = reservationService.viewValidReservationByHotelId(hotelId);
		assertNotNull(reservations);
		assertFalse(reservations.isEmpty());
		for (Reservation reservation : reservations) {
			assertEquals(hotelId, reservation.getHotel().getHotelId());

		}
	}

	@Disabled
	@Test
	void testReservationRoom() throws RoomNotAvailableException, RoomNotFoundException, UserNotFoundException, InconsistentHotelException {
	    // Arrange: Prepare test data
	    Long userId = 2L;

	    List<BookedRoomDTO> bookedRooms = new ArrayList<>();
	    
		Long roomId = 5L;
		int numberOfAdults = 2;
		int numberOfChildren = 1;

	    LocalDate checkInDate = LocalDate.now();
	    LocalDate checkOutDate = LocalDate.now().plusDays(7);

	    // Create a User
	    User user = new User();
	    user.setUserId(userId);
	    // Save the user to the in-memory database
	    userRepository.save(user);

	    // Create a Room
	    Room room = new Room();
	    // Save the room to the in-memory database
	    roomRepository.save(room);

	    // Act: Call the method to be tested
	    try {
	        boolean reservationResult = reservationService.reservationRoom(userId, bookedRooms, checkInDate, checkOutDate);

	        // Assert: Validate the result
	        assertTrue(reservationResult, "Reservation should be successful");
	    } catch (Exception e) {
	        fail("Exception occurred: " + e.getMessage());
	    }
	}

	@Test
	void testGetUserReservations() {
		Long userId = 3L;
		List<Reservation> reservations = reservationService.getUserReservations(userId);
		assertNotNull(reservations);
	}

	

	@Disabled
	@Test
	void testCancelReservationAndRequestRefund() {
		Long userId = 1L;
		Long reservationId = 1L;
		assertDoesNotThrow(() -> reservationService.cancelReservationAndRequestRefund(userId, reservationId));
	}

	@Disabled
	@Test
	void testRefundAmount() throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
		Long reservationId = 5L;
		Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();

		double refundedAmount = reservationService.refundAmount(reservationId);

		assertTrue(reservation.isRefundProcessed());
		assertNotNull(reservationRepository.findById(reservationId));
	}

}
