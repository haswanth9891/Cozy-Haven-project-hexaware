package com.hexaware.ccozyhaven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.repository.HotelRepository;

@Service
public class HotelServiceImp implements IHotelService{
	
	@Autowired
	private HotelRepository hotelRepository;

	@Override
	public List<Hotel> getAllHotels() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel getHotelDetailsById(Long hotelId) {
		return hotelRepository.findById(hotelId).orElse(null);
	}

	@Override
	public List<Room> getAllAvailableRoomsInHotel(Long hotelId) {
		return hotelRepository.findByHotelIdAndRoomAvailabilityStatus(hotelId, true);
	}
	

}
