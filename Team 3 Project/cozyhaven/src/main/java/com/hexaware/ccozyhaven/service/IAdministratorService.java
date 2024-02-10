package com.hexaware.ccozyhaven.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.dto.AdministratorDTO;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

@Repository
public interface IAdministratorService {

	String login(String username, String password);

	boolean register(AdministratorDTO adminDto) throws DataAlreadyPresentException;

	

	// Delete user account by user ID
	void deleteUserAccount(Long userId) throws UserNotFoundException;

	// Delete hotel owner account by hotel owner ID
	void deleteHotelOwnerAccount(Long hotelOwnerId) throws UserNotFoundException;

	// View all user accounts
	List<User> viewAllUser();

	// View all hotel owner accounts
	List<HotelOwner> viewAllHotelOwner();

	// Manage room reservation in hotel by the user
	void manageRoomReservation(Long reservationId, String reservationStatus)
			throws ReservationNotFoundException, InvalidCancellationException;

	

}
