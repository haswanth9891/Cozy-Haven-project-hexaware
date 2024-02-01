package com.hexaware.ccozyhaven.service;



import org.hibernate.mapping.List;

import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Room;






public interface IHotelOwnerService {
	
	// HotelOwner registration
    boolean registerHotelOwner(HotelOwner hotelOwner);
	
	// Hotel owner login
    boolean loginHotelOwner(String username, String password);

    // Add a new room to the hotel
    boolean addRoom(Long hotelId, Room roomDetails);

    // Edit details of an existing room in the hotel
    boolean editRoom(Long roomId, Room updatedRoomDetails);

    // Remove a room from the hotel
    boolean removeRoom(Long roomId);

    // View booked tickets by users for rooms in the hotel
    List viewReservation(Long hotelId);

    // Refund amount for canceled booked tickets
    boolean refundAmount(Long reservationId);
}