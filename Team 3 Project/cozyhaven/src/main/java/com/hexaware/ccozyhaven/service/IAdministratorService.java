package com.hexaware.ccozyhaven.service;




import java.util.List;

import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.dto.AdministratorDTO;
import com.hexaware.ccozyhaven.entities.Administrator;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.User;




@Repository
public interface IAdministratorService {
	
	//Administrator registration
	Administrator registerAdministrator(AdministratorDTO adminDTO);
	
	
	// Administrator login
	boolean loginAdministrator(String username, String password);

    // Delete user account by user ID
    boolean deleteUserAccount(Long userId);

    // Delete hotel owner account by hotel owner ID
    boolean deleteHotelOwnerAccount(Long hotelOwnerId);

    // View all user accounts
    List<User> viewAllUser();

    // View all hotel owner accounts
    List<HotelOwner> viewAllHotelOwner();

    // Manage room reservation in hotel by the user
    boolean manageRoomReservation(Long reservationId, String reservationStatus);

}
