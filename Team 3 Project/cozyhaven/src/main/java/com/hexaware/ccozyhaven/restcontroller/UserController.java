package com.hexaware.ccozyhaven.restcontroller;

import java.security.Provider.Service;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.UserDTO;
import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;

import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cozyhaven-user")
public class UserController {
	
		@Autowired
	    private IUserService userService;
		
		
//		@PostMapping("/addUser")
//		public User addUser(@RequestBody @Valid User user) {
//			
//			 return userService.addUser(user);
//		}
		
		@PutMapping("/updateuser/{userId}")
		public User updateUser(@PathVariable Long userId, @RequestBody @Valid UserDTO userDTO) throws UserNotFoundException {
			 
			return userService.updateUser(userId, userDTO);
		}
		
		@GetMapping("/searchRooms")
	    public List<Room> searchRooms(
	            @RequestParam String location,
	            @RequestParam LocalDate checkInDate,
	            @RequestParam LocalDate checkOutDate,
	            @RequestParam int numberOfRooms) {

	        return userService.searchRooms(location, checkInDate, checkOutDate, numberOfRooms);
	    }
	    
	    @GetMapping("/hotels")
	    public List<Hotel> getAllHotels() {
	        return userService.getAllHotels();
	    }
	    
	    @GetMapping("/hotels/{hotelId}")
	    public Hotel getHotelDetailsById(@PathVariable Long hotelId) {
	        return userService.getHotelDetailsById(hotelId);
	    }
	    
	    @GetMapping("/hotels/{hotelId}/available-rooms")
	    public List<Room> getAllAvailableRoomsInHotel(@PathVariable Long hotelId) {
	        return userService.getAllAvailableRoomsInHotel(hotelId);
	    }
	    
	    @GetMapping("/rooms/{roomId}/availability")
	    public boolean isRoomAvailable(
	            @PathVariable Long roomId,
	            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
	            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) throws RoomNotFoundException {
	        return userService.isRoomAvailable(roomId, checkInDate, checkOutDate);
	    }
	    
	    @GetMapping("/rooms/{roomId}/calculateTotalFare")
	    public double calculateTotalFare(
	            @PathVariable Long roomId,
	            @RequestParam int numberOfAdults,
	            @RequestParam int numberOfChildren) throws RoomNotFoundException {
	        return userService.calculateTotalFare(roomId, numberOfAdults, numberOfChildren);
	    }
	    
	    //to be reviewd
//	    @PostMapping("/reservation")
//	    public boolean reservationRoom(@RequestBody Reservation reservation)
//	            throws RoomNotAvailableException, RoomNotFoundException, UserNotFoundException {
//	        Long userId = reservation.getUser().getUserId();
//	        //Long roomId = reservation.getRooms().
//	        int numberOfAdults = reservation.getNumberOfAdults();
//	        int numberOfChildren = reservation.getNumberOfChildren();
//	        LocalDate checkInDate = reservation.getCheckInDate();
//	        LocalDate checkOutDate = reservation.getCheckOutDate();
//
//	        return userService.reservationRoom(userId, roomId, numberOfAdults, numberOfChildren, checkInDate, checkOutDate);
//	    }
	    
	    @GetMapping("/{userId}/reservations")
	    public List<Reservation> getUserReservations(@PathVariable Long userId) {
	        return userService.getUserReservations(userId);
	    }
	    
	    @DeleteMapping("/{userId}/reservations/{reservationId}")
	    public void cancelReservation(@PathVariable Long userId, @PathVariable Long reservationId)
	            throws ReservationNotFoundException {
	        userService.cancelReservation(userId, reservationId);
	    }
	    
	    @DeleteMapping("/{userId}/reservations/{reservationId}/cancelAndRefund")
	    public void cancelReservationAndRequestRefund(@PathVariable Long userId, @PathVariable Long reservationId)
	            throws InvalidCancellationException, ReservationNotFoundException {
	        userService.cancelReservationAndRequestRefund(userId, reservationId);
	    }
	    
	    
}
