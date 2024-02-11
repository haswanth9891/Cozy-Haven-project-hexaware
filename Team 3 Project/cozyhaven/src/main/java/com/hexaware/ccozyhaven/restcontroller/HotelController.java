package com.hexaware.ccozyhaven.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;
import com.hexaware.ccozyhaven.service.IHotelService;
/*
 * Author: Nafisa
 * 
 * Controller description: Handles HTTP requests related to the Hotel entity.
 * It contains methods for registering a new Hotel, logging in, updating details, etc.
 */

@RestController
@RequestMapping("/api/hotel")
public class HotelController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HotelController.class);
	
	@Autowired
	private IHotelService hotelService;
	
	@GetMapping("/getall")
    public List<Hotel> getAllHotels() {
		LOGGER.info("Received request to get all hotels");
        return hotelService.getAllHotels();
    }
    
    @GetMapping("/get/{hotelId}")
    public Hotel getHotelDetailsById(@PathVariable Long hotelId) throws HotelNotFoundException {
    	 LOGGER.info("Received request to get hotel details for ID: {}", hotelId);
        return hotelService.getHotelDetailsById(hotelId);
    }
    
    @GetMapping("/available-rooms/{hotelId}")
    public List<Room> getAllAvailableRoomsInHotel(@PathVariable Long hotelId) {
    	LOGGER.info("Received request to get all available rooms for hotel with ID: {}", hotelId);
        return hotelService.getAllAvailableRoomsInHotel(hotelId);
    }

}
