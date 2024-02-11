package com.hexaware.ccozyhaven.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.BookedRoomDTO;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;
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

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

	@Autowired
	private IReservationService reservationService;

	 @GetMapping("/get-by-hotel/{hotelId}")
	 @PreAuthorize("hasAnyAuthority('HOTEL_OWNER', 'ADMIN')")
	    public List<Reservation> viewReservationByHotelId(@PathVariable Long hotelId) throws HotelNotFoundException {
		 LOGGER.info("Received request to view reservations by hotel ID: {}", hotelId);
	        List<Reservation> reservations = reservationService.viewReservationByHotelId(hotelId);
			return reservations;
	    }
	 @GetMapping("/valid-get-by-hotel/{hotelId}")
	 @PreAuthorize("hasAnyAuthority('HOTEL_OWNER', 'ADMIN')")
	    public List<Reservation> viewValidReservationByHotelId(@PathVariable Long hotelId) throws HotelNotFoundException {
		 LOGGER.info("Received request to view valid reservations by hotel ID: {}", hotelId);
	        List<Reservation> reservations = reservationService.viewValidReservationByHotelId(hotelId);
			return reservations;
	    }

	 
	@GetMapping("/refundAmount/{reservationId}")
	@PreAuthorize("hasAuthority('HOTEL_OWNER')")
	public Double refundAmount(@PathVariable Long reservationId)
			throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
		 LOGGER.info("Received request to get refund amount for reservation ID: {}", reservationId);
		Double refundedAmount = reservationService.refundAmount(reservationId);
		return  refundedAmount;
	}
	
	@GetMapping("/get-by-user/{userId}")
	public List<Reservation> getUserReservations(@PathVariable Long userId) {
		 LOGGER.info("Received request to view reservations by user ID: {}", userId);
        return reservationService.getUserReservations(userId);
    }
    

    
    @DeleteMapping("/cancel-and-refund/{userId}/{reservationId}")
    @PreAuthorize("hasAuthority('USER')")
    public void cancelReservationAndRequestRefund(@PathVariable Long userId, @PathVariable Long reservationId)
            throws InvalidCancellationException, ReservationNotFoundException {
    	 LOGGER.info("Received request to cancel and refund reservation with ID: {} for user ID: {}", reservationId, userId);
    	reservationService.cancelReservationAndRequestRefund(userId, reservationId);
    }
    
    
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/make-reservation")
    public ResponseEntity<String> reserveRooms(
            @RequestParam Long userId,
            @RequestBody List<BookedRoomDTO> bookedRooms,
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate) {

        try {
            LocalDate checkIn = LocalDate.parse(checkInDate);
            LocalDate checkOut = LocalDate.parse(checkOutDate);

            boolean reservationSuccess = reservationService.reservationRoom(userId, bookedRooms, checkIn, checkOut);

            if (reservationSuccess) {
                return new ResponseEntity<>("Reservation successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Reservation failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (RoomNotAvailableException | RoomNotFoundException | UserNotFoundException | InconsistentHotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    } 
    
   

}
