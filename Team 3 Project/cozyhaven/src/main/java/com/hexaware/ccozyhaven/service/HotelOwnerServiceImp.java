package com.hexaware.ccozyhaven.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.HotelOwner;

import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;

import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.HotelRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HotelOwnerServiceImp implements IHotelOwnerService {

	@Autowired
	HotelOwnerRepository hotelOwnerRepository;

	@Autowired
	HotelRepository hotelRepository;

	Logger log = LoggerFactory.getLogger(HotelOwnerServiceImp.class);

	@Override
	public void addHotelOwnerWithHotel(HotelOwner hotelOwner) {

		log.info("Before saving: " + hotelOwner);

		Hotel hotel = hotelOwner.getHotel();

		hotel.setHotelOwner(hotelOwner);
		hotelOwner.setHotel(hotel);

		hotelRepository.save(hotel);
		hotelOwnerRepository.save(hotelOwner);

	}

	@Override
	public HotelOwner updateHotelOwner(Long hotelOwnerId, HotelOwner updatedHotelOwner)
			throws HotelOwnerNotFoundException {

		HotelOwner existingHotelOwner = hotelOwnerRepository.findById(hotelOwnerId)
				.orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

		existingHotelOwner.setHotelOwnerName(updatedHotelOwner.getHotelOwnerName());
		existingHotelOwner.setEmail(updatedHotelOwner.getEmail());
		existingHotelOwner.setGender(updatedHotelOwner.getGender());
		existingHotelOwner.setAddress(updatedHotelOwner.getAddress());
		existingHotelOwner.setHotel(updatedHotelOwner.getHotel());

		return hotelOwnerRepository.save(existingHotelOwner);

	}

	@Override
	public void deleteHotelOwner(Long hotelOwnerId) throws HotelOwnerNotFoundException {
		HotelOwner hotelOwnerToDelete = hotelOwnerRepository.findById(hotelOwnerId)
				.orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

		hotelOwnerRepository.delete(hotelOwnerToDelete);

	}



//	@Override
//	public HotelOwner registerHotelOwner(HotelOwnerDTO hotelOwnerDTO) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean loginHotelOwner(String username, String password) {
//		// TODO Auto-generated method stub
//		return false;
//	}

}
