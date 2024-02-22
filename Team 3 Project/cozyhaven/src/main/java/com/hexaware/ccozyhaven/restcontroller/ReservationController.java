package com.hexaware.ccozyhaven.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.BookedRoomDTO;
import com.hexaware.ccozyhaven.dto.MakeReservationDTO;
import com.hexaware.ccozyhaven.entities.Reservation;

import com.hexaware.ccozyhaven.exceptions.InconsistentHotelException;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotAvailableException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.service.IReservationService;

/*
 * Author: Nafisa
 * 
 * Controller description: Handles HTTP requests related to the Reservation entity.
 * It contains methods for registering a new Reservation, logging in, updating details, etc.
 */

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

	private final IReservationService reservationService;

	@Autowired
	public ReservationController(IReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping("/get-by-hotel/{hotelId}")
	@PreAuthorize("hasAnyAuthority('HOTEL_OWNER', 'ADMIN')")
	public List<Reservation> viewReservationByHotelId(@PathVariable Long hotelId) {
		LOGGER.info("Received request to view reservations by hotel ID: {}", hotelId);
		return reservationService.viewReservationByHotelId(hotelId);

	}

	@GetMapping("/valid-get-by-hotel/{hotelId}")
	@PreAuthorize("hasAnyAuthority('HOTEL_OWNER', 'ADMIN')")
	public List<Reservation> viewValidReservationByHotelId(@PathVariable Long hotelId) {
		LOGGER.info("Received request to view valid reservations by hotel ID: {}", hotelId);
		return reservationService.viewValidReservationByHotelId(hotelId);

	}

	@GetMapping("/refundAmount/{reservationId}")
	@PreAuthorize("hasAuthority('HOTEL_OWNER')")
	public Double refundAmount(@PathVariable Long reservationId)
			throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
		LOGGER.info("Received request to get refund amount for reservation ID: {}", reservationId);
		return reservationService.refundAmount(reservationId);

	}

	@GetMapping("/get-by-user/{userId}")
	@PreAuthorize("hasAuthority('HOTEL_OWNER','ADMIN','USER')")
	public List<Reservation> getUserReservations(@PathVariable Long userId) {
		LOGGER.info("Received request to view reservations by user ID: {}", userId);
		return reservationService.getUserReservations(userId);
	}

	@DeleteMapping("/cancel-and-refund/{userId}/{reservationId}")
	@PreAuthorize("hasAuthority('USER')")
	public void cancelReservationAndRequestRefund(@PathVariable Long userId, @PathVariable Long reservationId)
			throws InvalidCancellationException, ReservationNotFoundException {
		LOGGER.info("Received request to cancel and refund reservation with ID: {} for user ID: {}", reservationId,
				userId);
		reservationService.cancelReservationAndRequestRefund(userId, reservationId);
	}

	@PostMapping("/make-reservation")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<String> reserveRooms( @RequestBody MakeReservationDTO makeReservationDTO) {

		try {
			LocalDate checkIn = LocalDate.parse(makeReservationDTO.getCheckInDate());
			LocalDate checkOut = LocalDate.parse(makeReservationDTO.getCheckOutDate());

			boolean reservationSuccess = reservationService.reservationRoom(makeReservationDTO.getUserId(), makeReservationDTO.getBookedRoomDTO(), checkIn, checkOut);

			if (reservationSuccess) {
				return new ResponseEntity<>("Reservation is Pending, make Payment", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Reservation failed", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (RoomNotAvailableException | RoomNotFoundException | UserNotFoundException
				| InconsistentHotelException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/manage-room-reservation/{reservationId}")
	@PreAuthorize("hasAuthority('ADMIN','HOTEL_OWNER')")
	public String manageRoomReservation(@PathVariable Long reservationId,
			@RequestParam(name = "reservationStatus") String reservationStatus)
			throws ReservationNotFoundException, InvalidCancellationException {
		LOGGER.info("Received request to manage room reservation with ID: {} and status: {}", reservationId,
				reservationStatus);
		reservationService.manageRoomReservation(reservationId, reservationStatus);
		return "Room reservation managed successfully";
	}

}
