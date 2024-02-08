package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotAvailableException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.repository.ReservationRepository;

@SpringBootTest
class ReservationServiceImpTest {

	@Autowired
	IReservationService reservationService;

	@Autowired
	ReservationRepository reservationRepository;

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
	void testReservationRoom() throws RoomNotAvailableException, RoomNotFoundException, UserNotFoundException {
		Long userId = 2L;
		Long roomId = 5L;
		int numberOfAdults = 2;
		int numberOfChildren = 1;
		LocalDate checkInDate = LocalDate.now();
		LocalDate checkOutDate = LocalDate.now().plusDays(7);

		boolean result = reservationService.reservationRoom(userId, roomId, numberOfAdults, numberOfChildren,
				checkInDate, checkOutDate);
		assertTrue(result);
	}

	@Test
	void testGetUserReservations() {
		Long userId = 3L;
		List<Reservation> reservations = reservationService.getUserReservations(userId);
		assertNotNull(reservations);
	}

	@Disabled
	@Test
	void testCancelReservation() {
		Long userId = 2L;
		Long reservationId = 5L;
		assertDoesNotThrow(() -> reservationService.cancelReservation(userId, reservationId));
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
