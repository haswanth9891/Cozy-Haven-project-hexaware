package com.hexaware.ccozyhaven.service;





import java.util.List;

import com.hexaware.ccozyhaven.dto.HotelDTO;
import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;
import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.entities.Reservation;






public interface IHotelOwnerService {
	
//	// HotelOwner registration
//    HotelOwner registerHotelOwner(HotelOwnerDTO hotelOwnerDTO);
//	
//	// Hotel owner login
//    boolean loginHotelOwner(String username, String password);
    
    //update HotelOwnerDetails
    HotelOwner updateHotelOwner(Long hotelOwnerId, HotelOwnerDTO updatedHotelOwnerDTO) throws HotelOwnerNotFoundException;

    // Add a new room to the hotel
    Room addRoom(RoomDTO roomDTO);

    // Edit details of an existing room in the hotel
    Room editRoom(Long roomId, RoomDTO updatedRoomDTO) throws RoomNotFoundException;

    // Remove a room from the hotel
    void removeRoom(Long roomId) throws RoomNotFoundException;

    // View booked reservations by users for rooms in the hotel
    List<Reservation> viewReservation(Long hotelId);

    // Refund amount for canceled booked tickets
    double refundAmount(Long reservationId) throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException;

	void deleteHotelOwner(Long hotelOwnerId) throws HotelOwnerNotFoundException;

	void addHotelOwnerWithHotel(HotelOwner hotelOwner);

	Hotel addHotel(Long hotelOwnerId, HotelDTO hotelDTO) throws HotelOwnerNotFoundException;

	
}