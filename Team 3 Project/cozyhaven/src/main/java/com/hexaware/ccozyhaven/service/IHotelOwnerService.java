package com.hexaware.ccozyhaven.service;






import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;

import com.hexaware.ccozyhaven.entities.HotelOwner;

import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;







public interface IHotelOwnerService {
	
//	// HotelOwner registration
//    HotelOwner registerHotelOwner(HotelOwnerDTO hotelOwnerDTO);
//	
//	// Hotel owner login
//    boolean loginHotelOwner(String username, String password);
    
    //update HotelOwnerDetails
  
   

    // Add a new room to the hotel
    //Room addRoom(RoomDTO roomDTO);

    // Edit details of an existing room in the hotel
    //Room editRoom(Long roomId, RoomDTO updatedRoomDTO) throws RoomNotFoundException;

    // Remove a room from the hotel
    //void removeRoom(Long roomId) throws RoomNotFoundException;

    // View booked reservations by users for rooms in the hotel
    //List<Reservation> viewReservation(Long hotelId);

    // Refund amount for canceled booked tickets
   // double refundAmount(Long reservationId) throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException;

	void deleteHotelOwner(Long hotelOwnerId) throws HotelOwnerNotFoundException;

	

	void addHotelOwnerWithHotel(HotelOwnerDTO hotelOwnerDTO);

	void updateHotelOwnerWithHotel(Long hotelOwnerId, HotelOwnerDTO updatedHotelOwnerDTO)
			throws HotelOwnerNotFoundException;

	

	//Hotel addHotel(Long hotelOwnerId, HotelDTO hotelDTO) throws HotelOwnerNotFoundException;

	
}