package com.hexaware.ccozyhaven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.hexaware.ccozyhaven.dto.HotelDTO;
import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.HotelRepository;

import jakarta.transaction.Transactional;
/*
 * Author: Nafisa
 * 
 * Service description: Provides business logic related to the HotelOwner entity.
 * It contains methods for registering a new HotelOwner, logging in, updating details, etc.
 */

@Service
@Transactional
public class HotelOwnerServiceImp implements IHotelOwnerService {

	@Autowired
	HotelOwnerRepository hotelOwnerRepository;

	@Autowired
	HotelRepository hotelRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	private static final Logger LOGGER = LoggerFactory.getLogger(HotelOwnerServiceImp.class);

	@Override
	public boolean login(String username, String password) {
		LOGGER.info("Hotel Owner is loggin in...");
		return false;

	}

	@Override
	public Long registerHotelOwner(HotelOwnerDTO hotelOwnerDTO) throws DataAlreadyPresentException {

		

		LOGGER.info("Adding hotel owner with hotel: {}", hotelOwnerDTO);

		HotelOwner hotelOwner = convertHotelOwnerDTOToEntity(hotelOwnerDTO);
		HotelDTO hotelDTO = hotelOwnerDTO.getHotelDTO();
		Hotel hotel = convertHotelDTOToEntity(hotelDTO);
		hotelOwner.setRole("HOTEL_OWNER");
		hotel.setHotelOwner(hotelOwner);
		hotelOwner.setHotel(hotel);

		Hotel addedHotel = hotelRepository.save(hotel);
		HotelOwner addedHotelOwner = hotelOwnerRepository.save(hotelOwner);

		if ((addedHotelOwner != null) && (addedHotel != null)) {
			LOGGER.info("Registerd Hotel Owner with Hotel Details: " + addedHotelOwner + " " + addedHotel);
			return addedHotelOwner.getHotelOwnerId();
		}
		LOGGER.error("Hotel Owner not registered");
		return null;

	}

	private HotelOwner convertHotelOwnerDTOToEntity(HotelOwnerDTO hotelOwnerDTO) {
		HotelOwner hotelOwner = new HotelOwner();

		hotelOwner.setHotelOwnerName(hotelOwnerDTO.getHotelOwnerName());
		hotelOwner.setPassword(passwordEncoder.encode(hotelOwnerDTO.getPassword()));
		hotelOwner.setEmail(hotelOwnerDTO.getEmail());
		hotelOwner.setUsername(hotelOwnerDTO.getUsername());
		hotelOwner.setGender(hotelOwnerDTO.getGender());
		hotelOwner.setAddress(hotelOwnerDTO.getAddress());
		return hotelOwner;
	}

	private Hotel convertHotelDTOToEntity(HotelDTO hotelDTO) {
		Hotel hotel = new Hotel();

		hotel.setHotelName(hotelDTO.getHotelName());
		hotel.setLocation(hotelDTO.getLocation());
		hotel.setHasDining(hotelDTO.isHasDining());
		hotel.setHasParking(hotelDTO.isHasParking());
		hotel.setHasFreeWiFi(hotelDTO.isHasFreeWiFi());
		hotel.setHasRoomService(hotelDTO.isHasRoomService());
		hotel.setHasSwimmingPool(hotelDTO.isHasSwimmingPool());
		hotel.setHasFitnessCenter(hotelDTO.isHasFitnessCenter());
		return hotel;
	}

	@Override
	
	public void updateHotelOwnerWithHotel(Long hotelOwnerId, HotelOwnerDTO updatedHotelOwnerDTO)
			throws HotelOwnerNotFoundException, AuthorizationException, UnauthorizedAccessException {

		LOGGER.info("Updating hotel owner with ID: {}", hotelOwnerId);
		HotelOwner existingHotelOwner = hotelOwnerRepository.findById(hotelOwnerId)
				.orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

		HotelOwner updatedHotelOwner = convertHotelOwnerDTOToEntity(updatedHotelOwnerDTO);

		existingHotelOwner.setHotelOwnerName(updatedHotelOwner.getHotelOwnerName());
		existingHotelOwner.setEmail(updatedHotelOwner.getEmail());
		existingHotelOwner.setGender(updatedHotelOwner.getGender());
		existingHotelOwner.setUsername(updatedHotelOwner.getUsername());
		existingHotelOwner.setAddress(updatedHotelOwner.getAddress());

		HotelDTO updatedHotelDTO = updatedHotelOwnerDTO.getHotelDTO();
		Hotel existingHotel = existingHotelOwner.getHotel();

		existingHotel.setHotelName(updatedHotelDTO.getHotelName());
		existingHotel.setLocation(updatedHotelDTO.getLocation());
		existingHotel.setHasDining(updatedHotelDTO.isHasDining());
		existingHotel.setHasParking(updatedHotelDTO.isHasParking());
		existingHotel.setHasFreeWiFi(updatedHotelDTO.isHasFreeWiFi());
		existingHotel.setHasRoomService(updatedHotelDTO.isHasRoomService());
		existingHotel.setHasSwimmingPool(updatedHotelDTO.isHasSwimmingPool());
		existingHotel.setHasFitnessCenter(updatedHotelDTO.isHasFitnessCenter());

		hotelOwnerRepository.save(existingHotelOwner);

		LOGGER.info("Hotel owner and associated hotel details updated successfully");
	}

	@Override
	
	public String deleteHotelOwner(Long hotelOwnerId)
			throws HotelOwnerNotFoundException, AuthorizationException, UnauthorizedAccessException {
		LOGGER.info("Deleting hotel owner with ID: {}", hotelOwnerId);
		HotelOwner hotelOwnerToDelete = hotelOwnerRepository.findById(hotelOwnerId)
				.orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

		hotelOwnerRepository.delete(hotelOwnerToDelete);
		LOGGER.info("Hotel owner deleted successfully");

		return "Hotel Owner with ID: " + hotelOwnerId + "deleted succesfully";
	}

	

}
