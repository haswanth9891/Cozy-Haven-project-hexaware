package com.hexaware.ccozyhaven.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.AdministratorDTO;
import com.hexaware.ccozyhaven.dto.LoginDTO;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.service.IAdministratorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdministratorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorController.class);

	@Autowired
	private IAdministratorService administratorService;
	
	@PostMapping("/createNewAdmin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public boolean createNewAdmin(@RequestBody @Valid AdministratorDTO adminDto) throws DataAlreadyPresentException {
		LOGGER.info("Request received to create new Admin: " + adminDto);
		return administratorService.register(adminDto);
	}
	

	@PostMapping("/login")
	public String login(@RequestBody LoginDTO loginDto) {
		LOGGER.info("Request received to login as user: " + loginDto.getUsername() + ", Password: "
				+ loginDto.getPassword());
		return administratorService.login(loginDto.getUsername(), loginDto.getPassword());
	}

	@DeleteMapping("/deleteUserAccount/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUserAccount(@PathVariable Long userId) throws UserNotFoundException {
		 LOGGER.info("Received request to delete user account with ID: {}", userId);
	       administratorService.deleteUserAccount(userId);
		return "User account deleted successfully";
	}

	@DeleteMapping("/deleteHotelOwnerAccount/{hotelOwnerId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteHotelOwnerAccount(@PathVariable Long hotelOwnerId)
			throws UserNotFoundException {
		 LOGGER.info("Received request to delete hotel owner account with ID: {}", hotelOwnerId);
		administratorService.deleteHotelOwnerAccount(hotelOwnerId);
		return "Hotel owner account deleted successfully";
	}

	@GetMapping("/viewAllUsers")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<User> viewAllUsers() {
		LOGGER.info("Received request to view all users");
		List<User> users = administratorService.viewAllUser();
		return users;
	}

	@GetMapping("/viewAllHotelOwners")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<HotelOwner> viewAllHotelOwners() {
		LOGGER.info("Received request to view all hotel owners");
		List<HotelOwner> hotelOwners = administratorService.viewAllHotelOwner();
		return hotelOwners;
	}

	@DeleteMapping("/manageRoomReservation/{reservationId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String manageRoomReservation(@PathVariable Long reservationId,
			@RequestParam(name = "reservationStatus") String reservationStatus)
			throws ReservationNotFoundException, InvalidCancellationException {
		 LOGGER.info("Received request to manage room reservation with ID: {} and status: {}", reservationId, reservationStatus);
		administratorService.manageRoomReservation(reservationId, reservationStatus);
		return "Room reservation managed successfully";
	}

}
