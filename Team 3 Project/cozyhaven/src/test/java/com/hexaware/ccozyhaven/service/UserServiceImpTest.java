package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.RoomNotAvailableException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

@SpringBootTest
class UserServiceImpTest {
	
	@Autowired
	private IUserService userService;

	@Test
	void testSearchRooms() {
		List<Room> rooms = userService.searchRooms("Location", LocalDate.now(), LocalDate.now().plusDays(7), 2);
        assertNotNull(rooms);
	}

	@Test
	void testGetAllHotels() {
		 List<Hotel> hotels = userService.getAllHotels();
	        assertNotNull(hotels);
		
	}

	
	@Test
	void testGetHotelDetailsById() {
		 Long hotelId = 1L;
	        Hotel hotel = userService.getHotelDetailsById(hotelId);
	        assertNotNull(hotel);
	        assertEquals(hotelId, hotel.getHotelId());
		
	}

	
	@Test
	void testGetAllAvailableRoomsInHotel() {
		
		Long hotelId = 1L;
        List<Room> rooms = userService.getAllAvailableRoomsInHotel(hotelId);
        assertNotNull(rooms);
		
	}

	@Disabled
	@Test
	void testIsRoomAvailable() throws RoomNotFoundException {
		Long roomId = 1L;
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.now().plusDays(7);

        

        boolean result = userService.isRoomAvailable(roomId, checkInDate, checkOutDate);
        assertTrue(result);
		
	}

	@Disabled
	@Test
	void testCalculateTotalFare() throws RoomNotFoundException {
		Long roomId = 1L;
        int numberOfAdults = 4;
        int numberOfChildren = 1;
        double totalFare = userService.calculateTotalFare(roomId, numberOfAdults, numberOfChildren);

        // Add assertions based on your expected totalFare
        assertEquals(140, totalFare);
		
	}

	
	@Test
	void testReservationRoom() throws RoomNotAvailableException, RoomNotFoundException, UserNotFoundException {
		 Long userId = 1L;
	        Long roomId = 1L;
	        int numberOfAdults = 2;
	        int numberOfChildren = 1;
	        LocalDate checkInDate = LocalDate.now();
	        LocalDate checkOutDate = LocalDate.now().plusDays(7);

	        

	        boolean result = userService.reservationRoom(userId, roomId, numberOfAdults, numberOfChildren, checkInDate, checkOutDate);
	        assertTrue(result);
		
	}

	
	@Test
	void testGetUserReservations() {
		 Long userId = 1L;
	        List<Reservation> reservations = userService.getUserReservations(userId);
	        assertNotNull(reservations);
		
	}

	
	@Test
	void testCancelReservation() {
		 Long userId = 1L;
	        Long reservationId = 1L;
	        assertDoesNotThrow(() -> userService.cancelReservation(userId, reservationId));
		
	}

	
	@Test
	void testCancelReservationAndRequestRefund() {
		Long userId = 1L;
        Long reservationId = 1L;
        assertDoesNotThrow(() -> userService.cancelReservationAndRequestRefund(userId, reservationId));
		
	}

}
