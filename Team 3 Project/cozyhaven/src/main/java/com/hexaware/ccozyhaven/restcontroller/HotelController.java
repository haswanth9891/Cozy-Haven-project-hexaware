package com.hexaware.ccozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.service.IHotelService;

@RestController
@RequestMapping("/cozyhaven-hotel")
public class HotelController {
	
	@Autowired
	private IHotelService hotelService;
	
	@GetMapping("/hotels")
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }
    
    @GetMapping("/hotels/{hotelId}")
    public Hotel getHotelDetailsById(@PathVariable Long hotelId) {
        return hotelService.getHotelDetailsById(hotelId);
    }
    
    @GetMapping("/hotels/{hotelId}/available-rooms")
    public List<Room> getAllAvailableRoomsInHotel(@PathVariable Long hotelId) {
        return hotelService.getAllAvailableRoomsInHotel(hotelId);
    }

}
