package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerMismatchException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.RoomRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class RoomServiceImpTest {
	
	
	@Autowired
    private RoomServiceImp roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @Disabled
    void testAddRoomToHotel() throws HotelNotFoundException, HotelOwnerMismatchException, UnauthorizedAccessException {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomSize("Standard");
        roomDTO.setBedType("double bed");
        roomDTO.setMaxOccupancy(2);
        roomDTO.setBaseFare(100.0);
        roomDTO.setAC(true);
        roomDTO.setAvailabilityStatus(true);

        Long hotelId = 1L;

        Room savedRoom = roomService.addRoomToHotel(roomDTO, hotelId);

        assertNotNull(savedRoom);
        assertNotNull(savedRoom.getRoomId());
        assertEquals(roomDTO.getRoomSize(), savedRoom.getRoomSize());
        assertEquals(roomDTO.getBedType(), savedRoom.getBedType());
        assertEquals(roomDTO.getMaxOccupancy(), savedRoom.getMaxOccupancy());
        assertEquals(roomDTO.getBaseFare(), savedRoom.getBaseFare());
        assertEquals(roomDTO.isAC(), savedRoom.isAC());
        assertEquals(roomDTO.isAvailabilityStatus(), savedRoom.isAvailabilityStatus());
    }

    @Test
    void testEditRoom() throws RoomNotFoundException, UnauthorizedAccessException, AuthorizationException {
       
        RoomDTO updatedRoomDTO = new RoomDTO();
        updatedRoomDTO.setRoomSize("Deluxe");
        updatedRoomDTO.setBedType("king size");
        updatedRoomDTO.setMaxOccupancy(3);
        updatedRoomDTO.setBaseFare(150.0);
        updatedRoomDTO.setAC(true);
        updatedRoomDTO.setAvailabilityStatus(false);

        Long roomId = 3L;

        
        Room editedRoom = roomService.editRoom(roomId, updatedRoomDTO);

       
        assertNotNull(editedRoom);
        assertEquals(updatedRoomDTO.getRoomSize(), editedRoom.getRoomSize());
        assertEquals(updatedRoomDTO.getBedType(), editedRoom.getBedType());
        assertEquals(updatedRoomDTO.getMaxOccupancy(), editedRoom.getMaxOccupancy());
        assertEquals(updatedRoomDTO.getBaseFare(), editedRoom.getBaseFare());
        assertEquals(updatedRoomDTO.isAC(), editedRoom.isAC());
        assertEquals(updatedRoomDTO.isAvailabilityStatus(), editedRoom.isAvailabilityStatus());
    }


    @Test
    void testRemoveRoom() throws RoomNotFoundException, UnauthorizedAccessException, AuthorizationException {
       
        Long roomIdToRemove = 3L;

     
        roomService.removeRoom(roomIdToRemove);

        assertThrows(RoomNotFoundException.class, () -> roomRepository.findById(roomIdToRemove).orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomIdToRemove)));
    }

    @Test
    void testSearchRooms() {
      
        String location = "Location XYZ";
        LocalDate checkInDate = LocalDate.of(2024, 2, 10);
        LocalDate checkOutDate = LocalDate.of(2024, 2, 15);

       
        List<Room> availableRooms = roomService.searchRooms(location, checkInDate, checkOutDate);

        assertNotNull(availableRooms);
        assertFalse(availableRooms.isEmpty());
        
    }


	@Test
	void testIsRoomAvailable() throws RoomNotFoundException {
	    // Arrange
	    Long roomId = 3L;
	    LocalDate checkInDate = LocalDate.of(2024, 3, 20);
	    LocalDate checkOutDate = LocalDate.of(2024, 3, 25);

	    // Act
	    boolean isAvailable = roomService.isRoomAvailable(roomId, checkInDate, checkOutDate);

	    // Assert
	    assertTrue(isAvailable);
	    
	}
	@Test
	void testCalculateTotalFare() throws RoomNotFoundException {
	    // Arrange
	    Long roomId = 3L;
	    int numberOfAdults = 2;
	    int numberOfChildren = 2;

	    // Act
	    double totalFare = roomService.calculateTotalFare(roomId, numberOfAdults, numberOfChildren);

	    // Assert
	    assertTrue(totalFare > 0);
	    
	}

}
