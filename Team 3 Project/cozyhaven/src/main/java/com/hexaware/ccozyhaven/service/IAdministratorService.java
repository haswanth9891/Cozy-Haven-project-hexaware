package com.hexaware.ccozyhaven.service;



import org.hibernate.mapping.List;




public interface IAdministratorService {
	
	
	// Administrator login
    boolean loginAdministrator(String username, String password);

    // Delete user account by user ID
    boolean deleteUserAccount(Long userId);

    // Delete hotel owner account by hotel owner ID
    boolean deleteHotelOwnerAccount(Long hotelOwnerId);

    // View all user accounts
    List viewAllUser();

    // View all hotel owner accounts
    List viewAllHotelOwner();

    // Manage room reservation in hotel by the user
    boolean manageRoomReservation(Long reservationId, String reservationStatus);

}
