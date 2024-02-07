package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotAvailableException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

class ReservationServiceImpTest {
	
	@Autowired
	IReservationService  reservationService;

	@Test
	void testRefundAmount() throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
		double refundedAmount = reservationService.refundAmount(1L);
	}

	@Test
	void testReservationRoom() throws RoomNotAvailableException, RoomNotFoundException, UserNotFoundException {
		 Long userId = 1L;
	        Long roomId = 1L;
	        int numberOfAdults = 2;
	        int numberOfChildren = 1;
	        LocalDate checkInDate = LocalDate.now();
	        LocalDate checkOutDate = LocalDate.now().plusDays(7);

	        

	        boolean result = reservationService.reservationRoom(userId, roomId, numberOfAdults, numberOfChildren, checkInDate, checkOutDate);
	        assertTrue(result);
	}


	@Test
	void testGetUserReservations() {
		 Long userId = 1L;
	        List<Reservation> reservations = reservationService.getUserReservations(userId);
	        assertNotNull(reservations);
	}

	@Test
	void testCancelReservation() {
		 Long userId = 1L;
	        Long reservationId = 1L;
	        assertDoesNotThrow(() -> reservationService.cancelReservation(userId, reservationId));
	}

	@Test
	void testCancelReservationAndRequestRefund() {
		Long userId = 1L;
        Long reservationId = 1L;
        assertDoesNotThrow(() -> reservationService.cancelReservationAndRequestRefund(userId, reservationId));
	}
	
//	@Test
//	void testViewReservation() {
//		List<Reservation> reservations = hotelOwnerService.viewReservation(1L);
//        assertNotNull(reservations);
//		
//	}

}
