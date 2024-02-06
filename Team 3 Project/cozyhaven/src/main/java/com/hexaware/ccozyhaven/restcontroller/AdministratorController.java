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
	public String deleteUserAccount(@PathVariable Long userId) throws UserNotFoundException {
		administratorService.deleteUserAccount(userId);
		return "User account deleted successfully";
	}

	@DeleteMapping("/deleteHotelOwnerAccount/{hotelOwnerId}")
	public String deleteHotelOwnerAccount(@PathVariable Long hotelOwnerId)
			throws UserNotFoundException {
		administratorService.deleteHotelOwnerAccount(hotelOwnerId);
		return "Hotel owner account deleted successfully";
	}

	@GetMapping("/viewAllUsers")
	public List<User> viewAllUsers() {
		List<User> users = administratorService.viewAllUser();
		return users;
	}

	@GetMapping("/viewAllHotelOwners")
	public List<HotelOwner> viewAllHotelOwners() {
		List<HotelOwner> hotelOwners = administratorService.viewAllHotelOwner();
		return hotelOwners;
	}

	@DeleteMapping("/manageRoomReservation/{reservationId}")
	public String manageRoomReservation(@PathVariable Long reservationId,
			@RequestParam(name = "reservationStatus") String reservationStatus)
			throws ReservationNotFoundException, InvalidCancellationException {
		administratorService.manageRoomReservation(reservationId, reservationStatus);
		return "Room reservation managed successfully";
	}

}
