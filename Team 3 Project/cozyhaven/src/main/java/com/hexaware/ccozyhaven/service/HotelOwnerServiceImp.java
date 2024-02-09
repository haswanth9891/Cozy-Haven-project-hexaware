package com.hexaware.ccozyhaven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.HotelDTO;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(HotelOwnerServiceImp.class);

	@Override
	public void addHotelOwnerWithHotel(HotelOwnerDTO hotelOwnerDTO) {

		LOGGER.info("Adding hotel owner with hotel: {}", hotelOwnerDTO);

	    // Convert DTOs to entities
	    HotelOwner hotelOwner = convertHotelOwnerDTOToEntity(hotelOwnerDTO);
	    HotelDTO hotelDTO = hotelOwnerDTO.getHotelDTO();
	    Hotel hotel = convertHotelDTOToEntity(hotelDTO);
	    

	    // Set bidirectional relationship
	    hotel.setHotelOwner(hotelOwner);
	    hotelOwner.setHotel(hotel);

	    // Save entities
	    hotelRepository.save(hotel);
	    hotelOwnerRepository.save(hotelOwner);

	    LOGGER.info("Hotel owner added successfully");

	}

	private HotelOwner convertHotelOwnerDTOToEntity(HotelOwnerDTO hotelOwnerDTO) {
	    HotelOwner hotelOwner = new HotelOwner();
	    
	    hotelOwner.setHotelOwnerName(hotelOwnerDTO.getHotelOwnerName());
	    hotelOwner.setPassword(hotelOwnerDTO.getPassword());
	    hotelOwner.setEmail(hotelOwnerDTO.getEmail());
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
	public void updateHotelOwnerWithHotel(Long hotelOwnerId, HotelOwnerDTO updatedHotelOwnerDTO) throws HotelOwnerNotFoundException {

	    LOGGER.info("Updating hotel owner with ID: {}", hotelOwnerId);

	    // Retrieve existing HotelOwner
	    HotelOwner existingHotelOwner = hotelOwnerRepository.findById(hotelOwnerId)
	            .orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

	    // Convert DTO to entity
	    HotelOwner updatedHotelOwner = convertHotelOwnerDTOToEntity(updatedHotelOwnerDTO);

	    // Update HotelOwner fields
	    existingHotelOwner.setHotelOwnerName(updatedHotelOwner.getHotelOwnerName());
	    existingHotelOwner.setEmail(updatedHotelOwner.getEmail());
	    existingHotelOwner.setGender(updatedHotelOwner.getGender());
	    existingHotelOwner.setAddress(updatedHotelOwner.getAddress());

	    // Update associated Hotel details
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

	    // Save the updated HotelOwner and associated Hotel
	    hotelOwnerRepository.save(existingHotelOwner);

	    LOGGER.info("Hotel owner and associated hotel details updated successfully");
	}

	
	
	@Override
	public void deleteHotelOwner(Long hotelOwnerId) throws HotelOwnerNotFoundException {
		LOGGER.info("Deleting hotel owner with ID: {}", hotelOwnerId);
		HotelOwner hotelOwnerToDelete = hotelOwnerRepository.findById(hotelOwnerId)
				.orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

		hotelOwnerRepository.delete(hotelOwnerToDelete);
		LOGGER.info("Hotel owner deleted successfully");

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
