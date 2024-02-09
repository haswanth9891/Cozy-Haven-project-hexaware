package com.hexaware.ccozyhaven.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorController.class);

	@Autowired
	private IAdministratorService administratorService;

	@DeleteMapping("/deleteUserAccount/{userId}")
	public String deleteUserAccount(@PathVariable Long userId) throws UserNotFoundException {
		 LOGGER.info("Received request to delete user account with ID: {}", userId);
	       administratorService.deleteUserAccount(userId);
		return "User account deleted successfully";
	}

	@DeleteMapping("/deleteHotelOwnerAccount/{hotelOwnerId}")
	public String deleteHotelOwnerAccount(@PathVariable Long hotelOwnerId)
			throws UserNotFoundException {
		 LOGGER.info("Received request to delete hotel owner account with ID: {}", hotelOwnerId);
		administratorService.deleteHotelOwnerAccount(hotelOwnerId);
		return "Hotel owner account deleted successfully";
	}

	@GetMapping("/viewAllUsers")
	public List<User> viewAllUsers() {
		LOGGER.info("Received request to view all users");
		List<User> users = administratorService.viewAllUser();
		return users;
	}

	@GetMapping("/viewAllHotelOwners")
	public List<HotelOwner> viewAllHotelOwners() {
		LOGGER.info("Received request to view all hotel owners");
		List<HotelOwner> hotelOwners = administratorService.viewAllHotelOwner();
		return hotelOwners;
	}

	@DeleteMapping("/manageRoomReservation/{reservationId}")
	public String manageRoomReservation(@PathVariable Long reservationId,
			@RequestParam(name = "reservationStatus") String reservationStatus)
			throws ReservationNotFoundException, InvalidCancellationException {
		 LOGGER.info("Received request to manage room reservation with ID: {} and status: {}", reservationId, reservationStatus);
		administratorService.manageRoomReservation(reservationId, reservationStatus);
		return "Room reservation managed successfully";
	}

}
