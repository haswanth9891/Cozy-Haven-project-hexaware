package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;

class RoomServiceImpTest {
	
	@Autowired
	IRoomService roomService;

	@Test
	void testAddRoom() {
		RoomDTO roomDTO = new RoomDTO(2L ,"Deluxe", "Double Bed", 2, 150.0, true, true);
        Room addedRoom = roomService.addRoom(roomDTO);
        assertNotNull(addedRoom);
        assertEquals("Deluxe", addedRoom.getRoomSize());
	}

	@Test
	void testEditRoom() throws RoomNotFoundException {
		 RoomDTO updatedRoomDTO = new RoomDTO(1L,"Standard", "Single Bed", 1, 100.0, false, false);
	        Room editedRoom = roomService.editRoom(1L, updatedRoomDTO);
	        assertNotNull(editedRoom);
	        assertEquals("Standard", editedRoom.getRoomSize());
	}

//	@Test
//	void testRemoveRoom() throws RoomNotFoundException {
//		roomService.removeRoom(1L);
//		
//	}

	@Test
	void testSearchRooms() {
		List<Room> rooms = roomService.searchRooms("Location", LocalDate.now(), LocalDate.now().plusDays(7), 2);
        assertNotNull(rooms);
	}

	@Test
	void testIsRoomAvailable() throws RoomNotFoundException {
		Long roomId = 1L;
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.now().plusDays(7);

        

        boolean result = roomService.isRoomAvailable(roomId, checkInDate, checkOutDate);
        assertTrue(result);
	}

	@Test
	void testCalculateTotalFare() {
		fail("Not yet implemented");
	}

}
