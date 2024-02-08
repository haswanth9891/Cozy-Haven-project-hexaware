package com.hexaware.ccozyhaven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;
import com.hexaware.ccozyhaven.repository.HotelRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HotelServiceImp implements IHotelService{
	
	@Autowired
	private HotelRepository hotelRepository;

	@Override
	public List<Hotel> getAllHotels() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel getHotelDetailsById(Long hotelId) throws HotelNotFoundException {
		return hotelRepository.findById(hotelId).orElseThrow(()->  new HotelNotFoundException("Hotel not found with Id:" + hotelId));
	}

	@Override
	public List<Room> getAllAvailableRoomsInHotel(Long hotelId) {
		return hotelRepository.findAvailableRoomsInHotel(hotelId);
	}
	

}
