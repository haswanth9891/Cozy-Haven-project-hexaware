package com.hexaware.ccozyhaven.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.AdministratorDTO;
import com.hexaware.ccozyhaven.dto.AuthRequest;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.service.IAdministratorService;
import com.hexaware.ccozyhaven.service.JwtService;

import jakarta.validation.Valid;
/*
 * Author: Nafisa
 * 
 * Controller description: Handles HTTP requests related to the Administrator entity.
 * It contains methods for registering a new administrator, logging in, updating details, etc.
 */

@RestController
@RequestMapping("/api/admin")
public class AdministratorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorController.class);

	@Autowired
	private IAdministratorService administratorService;

	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/register")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public String createNewAdmin(@RequestBody @Valid AdministratorDTO adminDTO) throws DataAlreadyPresentException {
		LOGGER.info("Request received to create new Admin: " + adminDTO);
		long adminId = administratorService.register(adminDTO);

		if (adminId != 0) {
			return "Administrator added successfully ";
		} else {
			return "Failed to add administrator ";
		}
	}

	@PostMapping("/login")
	public String login(@RequestBody AuthRequest authRequest) {
		LOGGER.info("Request received to login as user: " + authRequest.getUsername() + ", Password: "
				+ authRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		String token = null;

		if (authentication.isAuthenticated()) {

			token = jwtService.generateToken(authRequest.getUsername());

			LOGGER.info("Tokent : " + token);

		} else {

			LOGGER.info("invalid");

			throw new UsernameNotFoundException("UserName or Password in Invalid / Invalid Request");

		}

		return token;
	}

	@DeleteMapping("/delete-user-account/{userId}")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUserAccount(@PathVariable Long userId) throws UserNotFoundException {
		LOGGER.info("Received request to delete user account with ID: {}", userId);
		administratorService.deleteUserAccount(userId);
		return "User account deleted successfully";
	}

	@DeleteMapping("/delete-hotelowner-account/{hotelOwnerId}")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteHotelOwnerAccount(@PathVariable Long hotelOwnerId) throws UserNotFoundException {
		LOGGER.info("Received request to delete hotel owner account with ID: {}", hotelOwnerId);
		administratorService.deleteHotelOwnerAccount(hotelOwnerId);
		return "Hotel owner account deleted successfully";
	}

	@GetMapping("/getall-users")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public List<User> viewAllUsers() {
		LOGGER.info("Received request to view all users");
		List<User> users = administratorService.viewAllUser();
		return users;
	}

	@GetMapping("/getall-hotelowners")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public List<HotelOwner> viewAllHotelOwners() {
		LOGGER.info("Received request to view all hotel owners");
		List<HotelOwner> hotelOwners = administratorService.viewAllHotelOwner();
		return hotelOwners;
	}

	@DeleteMapping("/manage-room-reservation/{reservationId}")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String manageRoomReservation(@PathVariable Long reservationId,
			@RequestParam(name = "reservationStatus") String reservationStatus)
			throws ReservationNotFoundException, InvalidCancellationException {
		LOGGER.info("Received request to manage room reservation with ID: {} and status: {}", reservationId,
				reservationStatus);
		administratorService.manageRoomReservation(reservationId, reservationStatus);
		return "Room reservation managed successfully";
	}

}
