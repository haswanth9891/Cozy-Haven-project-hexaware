package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;
import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;

@SpringBootTest
class HotelOwnerServiceImpTest {
	
	@Autowired
    private HotelOwnerServiceImp hotelOwnerService;

	@Test
	@Rollback(false)
	void testAddRoom() {
		RoomDTO roomDTO = new RoomDTO(2L ,"Deluxe", "Double Bed", 2, 150.0, true, true);
        Room addedRoom = hotelOwnerService.addRoom(roomDTO);
        assertNotNull(addedRoom);
        assertEquals("Deluxe", addedRoom.getRoomSize());
	}

	@Test
	void testUpdateHotelOwner() throws HotelOwnerNotFoundException {
		 Set<Hotel> hotels = new HashSet<>();
	        hotels.add(new Hotel());

	        // Creating an instance of HotelOwnerDTO using the provided constructor
	        HotelOwnerDTO hotelOwnerDTO = new HotelOwnerDTO(1L, "John Doe", "password123", "john@example.com", hotels);
		
        HotelOwner updatedHotelOwner = hotelOwnerService.updateHotelOwner(1L, hotelOwnerDTO);
        assertNotNull(updatedHotelOwner);
        assertEquals("Updated Owner", updatedHotelOwner.getHotelOwnerName());
		
	}

	@Test
	void testEditRoom() throws RoomNotFoundException {
		 RoomDTO updatedRoomDTO = new RoomDTO(1L,"Standard", "Single Bed", 1, 100.0, false, false);
	        Room editedRoom = hotelOwnerService.editRoom(1L, updatedRoomDTO);
	        assertNotNull(editedRoom);
	        assertEquals("Standard", editedRoom.getRoomSize());
		
	}

	@Test
	void testRemoveRoom() throws RoomNotFoundException {
		hotelOwnerService.removeRoom(1L);
	}

	@Test
	void testViewReservation() {
		List<Reservation> reservations = hotelOwnerService.viewReservation(1L);
        assertNotNull(reservations);
		
	}

	@Test
	void testRefundAmount() throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
		double refundedAmount = hotelOwnerService.refundAmount(1L);
		
	}

}
