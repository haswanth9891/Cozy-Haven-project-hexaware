package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Room;

class HotelServiceImpTest {
	
	@Autowired
	IHotelService hotelService;

	@Test
	void testGetAllHotels() {
		 List<Hotel> hotels = hotelService.getAllHotels();
	        assertNotNull(hotels);
	}

	@Test
	void testGetHotelDetailsById() {
		 Long hotelId = 1L;
	        Hotel hotel = hotelService.getHotelDetailsById(hotelId);
	        assertNotNull(hotel);
	        assertEquals(hotelId, hotel.getHotelId());
	}

	@Test
	void testGetAllAvailableRoomsInHotel() {
		Long hotelId = 1L;
        List<Room> rooms = hotelService.getAllAvailableRoomsInHotel(hotelId);
        assertNotNull(rooms);
	}

}
