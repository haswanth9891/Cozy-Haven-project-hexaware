package com.hexaware.ccozyhaven.service;

import java.util.List;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Room;

public interface IHotelService {
	
	public List<Hotel> getAllHotels();
	public Hotel getHotelDetailsById(Long hotelId);
	List<Room> getAllAvailableRoomsInHotel(Long hotelId);

}
