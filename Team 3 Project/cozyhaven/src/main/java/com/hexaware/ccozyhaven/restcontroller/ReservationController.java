package com.hexaware.ccozyhaven.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotAvailableException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.service.IReservationService;

@RestController
@RequestMapping("/cozyhaven-reservation")
public class ReservationController {

	@Autowired
	private IReservationService reservationService;

	 @GetMapping("/get-by-hotel/{hotelId}")
	    public List<Reservation> viewReservationByHotelId(@PathVariable Long hotelId) throws HotelNotFoundException {
	        List<Reservation> reservations = reservationService.viewReservationByHotelId(hotelId);
			return reservations;
	    }
	 @GetMapping("/valid-get-by-hotel/{hotelId}")
	    public List<Reservation> viewValidReservationByHotelId(@PathVariable Long hotelId) throws HotelNotFoundException {
	        List<Reservation> reservations = reservationService.viewValidReservationByHotelId(hotelId);
			return reservations;
	    }

	@GetMapping("/refundAmount/{reservationId}")
	public Double refundAmount(@PathVariable Long reservationId)
			throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
		Double refundedAmount = reservationService.refundAmount(reservationId);
		return  refundedAmount;
	}
	
	@GetMapping("/get-by-user/{userId}")
    public List<Reservation> getUserReservations(@PathVariable Long userId) {
        return reservationService.getUserReservations(userId);
    }
    
    @DeleteMapping("/cancel/{userId}/{reservationId}")
    public void cancelReservation(@PathVariable Long userId, @PathVariable Long reservationId)
            throws ReservationNotFoundException {
    	reservationService.cancelReservation(userId, reservationId);
    }
    
    @DeleteMapping("/cancel-and-refund/{userId}/{reservationId}")
    public void cancelReservationAndRequestRefund(@PathVariable Long userId, @PathVariable Long reservationId)
            throws InvalidCancellationException, ReservationNotFoundException {
    	reservationService.cancelReservationAndRequestRefund(userId, reservationId);
    }
    
    
    @PostMapping("/make-reservation")
    public ResponseEntity<String> reserveRooms(
            @RequestParam Long userId,
            @RequestBody Long roomId,
            @RequestParam int numberOfAdults,
            @RequestParam int numberOfChildren,
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate) {

        try {
            LocalDate checkIn = LocalDate.parse(checkInDate);
            LocalDate checkOut = LocalDate.parse(checkOutDate);

            boolean reservationSuccess = reservationService.reservationRoom(
                    userId, roomId, numberOfAdults, numberOfChildren, checkIn, checkOut);

            if (reservationSuccess) {
                return new ResponseEntity<>("Reservation successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Reservation failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (RoomNotAvailableException | RoomNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
   

}
