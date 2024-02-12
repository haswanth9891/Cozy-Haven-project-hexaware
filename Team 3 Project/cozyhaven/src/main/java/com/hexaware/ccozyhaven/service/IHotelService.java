package com.hexaware.ccozyhaven.service;

import java.util.List;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;

public interface IHotelService {
	//list all hotels
	public List<Hotel> getAllHotels();
	//get hotel Details By Id
	public Hotel getHotelDetailsById(Long hotelId) throws HotelNotFoundException;
	//get all available rooms in hotel
	List<Room> getAllAvailableRoomsInHotel(Long hotelId);

}
