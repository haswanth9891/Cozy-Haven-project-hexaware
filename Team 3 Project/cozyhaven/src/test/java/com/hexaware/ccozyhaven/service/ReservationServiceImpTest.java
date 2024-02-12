package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
	
	

	@Test
	void testViewReservationByHotelId() {

		Long hotelId = 1L;
		List<Reservation> reservations = reservationService.viewReservationByHotelId(hotelId);
		assertNotNull(reservations);
		assertFalse(reservations.isEmpty());
		for (Reservation reservation : reservations) {
			assertEquals(hotelId, reservation.getHotel().getHotelId());
		}
	}

	@Test
	void testViewValidReservationByHotelId() {

		Long hotelId = 1L;
		List<Reservation> reservations = reservationService.viewValidReservationByHotelId(hotelId);
		assertNotNull(reservations);
		assertFalse(reservations.isEmpty());
		for (Reservation reservation : reservations) {
			assertEquals(hotelId, reservation.getHotel().getHotelId());

		}
	}

	
	@Test
	void testReservationRoom() throws RoomNotAvailableException, RoomNotFoundException, UserNotFoundException, InconsistentHotelException {
	    // Arrange: Prepare test data
	    Long userId = 1L;

	    List<BookedRoomDTO> bookedRooms = new ArrayList<>();
	    
		Long roomId = 3L;
		int numberOfAdults = 2;
		int numberOfChildren = 1;

	    LocalDate checkInDate = LocalDate.now();
	    LocalDate checkOutDate = LocalDate.now().plusDays(7);

	    

	    
	    try {
	        boolean reservationResult = reservationService.reservationRoom(userId, bookedRooms, checkInDate, checkOutDate);

	        
	        assertTrue(reservationResult, "Reservation should be successful");
	    } catch (Exception e) {
	        fail("Exception occurred: " + e.getMessage());
	    }
	}

	@Test
	void testGetUserReservations() {
		Long userId = 1L;
		List<Reservation> reservations = reservationService.getUserReservations(userId);
		assertNotNull(reservations);
	}

	

	
	@Test
	void testCancelReservationAndRequestRefund() {
		Long userId = 1L;
		Long reservationId = 1L;
		assertDoesNotThrow(() -> reservationService.cancelReservationAndRequestRefund(userId, reservationId));
	}

	
	@Test
	void testRefundAmount() throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
		Long reservationId = 1L;
		Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
		reservation.setReservationStatus("CANCELLED");

		double refundedAmount = reservationService.refundAmount(reservationId);

		assertTrue(reservation.isRefundProcessed());
		assertNotNull(reservationRepository.findById(reservationId));
	}
	
	@Test
	void testManageRoomReservation() {

		assertDoesNotThrow(() -> reservationService.manageRoomReservation(1L, "CANCELLED"));

		Optional<Reservation> deletedReservation = reservationRepository.findById(1L);
		assert (!deletedReservation.isPresent());
	}

}
