package com.hexaware.ccozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.service.IAdministratorService;

@RestController
@RequestMapping("/cozyhaven-admin")
public class AdministratorController {
	
	 @Autowired
	    private IAdministratorService administratorService;
	 
	 @DeleteMapping("/deleteUserAccount/{userId}")
	    public ResponseEntity<String> deleteUserAccount(@PathVariable Long userId) throws UserNotFoundException {
	        administratorService.deleteUserAccount(userId);
	        return new ResponseEntity<>("User account deleted successfully", HttpStatus.OK);
	    }
	 
	 
	 @DeleteMapping("/deleteHotelOwnerAccount/{hotelOwnerId}")
	    public ResponseEntity<String> deleteHotelOwnerAccount(@PathVariable Long hotelOwnerId) throws UserNotFoundException {
	        administratorService.deleteHotelOwnerAccount(hotelOwnerId);
	        return new ResponseEntity<>("Hotel owner account deleted successfully", HttpStatus.OK);
	    }
	 
	 @GetMapping("/viewAllUsers")
	    public ResponseEntity<List<User>> viewAllUsers() {
	        List<User> users = administratorService.viewAllUser();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    }
	 
	 @GetMapping("/viewAllHotelOwners")
	    public ResponseEntity<List<HotelOwner>> viewAllHotelOwners() {
	        List<HotelOwner> hotelOwners = administratorService.viewAllHotelOwner();
	        return new ResponseEntity<>(hotelOwners, HttpStatus.OK);
	    }
	 
	 
	 @DeleteMapping("/manageRoomReservation/{reservationId}")
	    public ResponseEntity<String> manageRoomReservation(@PathVariable Long reservationId,
	                                                        @RequestParam(name = "reservationStatus") String reservationStatus)
	            throws ReservationNotFoundException, InvalidCancellationException {
	        administratorService.manageRoomReservation(reservationId, reservationStatus);
	        return new ResponseEntity<>("Room reservation managed successfully", HttpStatus.OK);
	    }

}
