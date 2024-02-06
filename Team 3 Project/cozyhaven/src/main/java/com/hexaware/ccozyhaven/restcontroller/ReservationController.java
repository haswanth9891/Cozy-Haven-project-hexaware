package com.hexaware.ccozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;

import com.hexaware.ccozyhaven.service.IReservationService;

@RestController
@RequestMapping("/cozyhaven-reservation")
public class ReservationController {

	@Autowired
	private IReservationService reservationService;

//	@GetMapping("/viewReservation/{hotelId}")
//	public List<Reservation> viewReservation(@PathVariable Long hotelId) {
//		List<Reservation> reservations = reservationService.viewReservation(hotelId);
//		return reservations;
//	}

	@GetMapping("/refundAmount/{reservationId}")
	public Double refundAmount(@PathVariable Long reservationId)
			throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
		Double refundedAmount = reservationService.refundAmount(reservationId);
		return  refundedAmount;
	}
	
	@GetMapping("/{userId}/reservations")
    public List<Reservation> getUserReservations(@PathVariable Long userId) {
        return reservationService.getUserReservations(userId);
    }
    
    @DeleteMapping("/{userId}/reservations/{reservationId}")
    public void cancelReservation(@PathVariable Long userId, @PathVariable Long reservationId)
            throws ReservationNotFoundException {
    	reservationService.cancelReservation(userId, reservationId);
    }
    
    @DeleteMapping("/{userId}/reservations/{reservationId}/cancelAndRefund")
    public void cancelReservationAndRequestRefund(@PathVariable Long userId, @PathVariable Long reservationId)
            throws InvalidCancellationException, ReservationNotFoundException {
    	reservationService.cancelReservationAndRequestRefund(userId, reservationId);
    }
    
    
    //to be reviewed
//    @PostMapping("/reservation")
//    public boolean reservationRoom(@RequestBody Reservation reservation)
//            throws RoomNotAvailableException, RoomNotFoundException, UserNotFoundException {
//        Long userId = reservation.getUser().getUserId();
//        //Long roomId = reservation.getRooms().
//        int numberOfAdults = reservation.getNumberOfAdults();
//        int numberOfChildren = reservation.getNumberOfChildren();
//        LocalDate checkInDate = reservation.getCheckInDate();
//        LocalDate checkOutDate = reservation.getCheckOutDate();
//
//        return userService.reservationRoom(userId, roomId, numberOfAdults, numberOfChildren, checkInDate, checkOutDate);
//    }

}
