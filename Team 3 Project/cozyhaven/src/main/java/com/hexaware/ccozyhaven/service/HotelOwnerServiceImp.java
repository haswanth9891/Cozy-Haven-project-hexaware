package com.hexaware.ccozyhaven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.HotelDTO;
import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
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
	public boolean login(String username, String password) {
		
		LOGGER.info("Hotel Owner is loggin in...");
		return false;
	}

	@Override
	public boolean registerHotelOwner(HotelOwnerDTO hotelOwnerDTO) throws DataAlreadyPresentException {
		try {
			HotelOwner hotelOwnerByEmail = getHotelOwnerByEmail(hotelOwnerDTO.getEmail());

			if (hotelOwnerDTO.getEmail().equals(hotelOwnerByEmail.getEmail())) {
				LOGGER.warn("User is trying to enter DUPLICATE data while registering");
				throw new DataAlreadyPresentException("Email already taken...Try Logging in..!");
			}

			LOGGER.info("Adding hotel owner with hotel: {}", hotelOwnerDTO);

			HotelOwner hotelOwner = convertHotelOwnerDTOToEntity(hotelOwnerDTO);
			HotelDTO hotelDTO = hotelOwnerDTO.getHotelDTO();
			Hotel hotel = convertHotelDTOToEntity(hotelDTO);

			hotel.setHotelOwner(hotelOwner);
			hotelOwner.setHotel(hotel);

			hotelRepository.save(hotel);
			hotelOwnerRepository.save(hotelOwner);

			LOGGER.info("Hotel owner added successfully");

			return true;
		} catch (DataAlreadyPresentException e) {
			LOGGER.error("Error registering hotel owner", e);
			return false;
		}
	}

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
	public void updateHotelOwnerWithHotel(Long hotelOwnerId, HotelOwnerDTO updatedHotelOwnerDTO)
			throws HotelOwnerNotFoundException {

		LOGGER.info("Updating hotel owner with ID: {}", hotelOwnerId);

		HotelOwner existingHotelOwner = hotelOwnerRepository.findById(hotelOwnerId)
				.orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

		HotelOwner updatedHotelOwner = convertHotelOwnerDTOToEntity(updatedHotelOwnerDTO);

		existingHotelOwner.setHotelOwnerName(updatedHotelOwner.getHotelOwnerName());
		existingHotelOwner.setEmail(updatedHotelOwner.getEmail());
		existingHotelOwner.setGender(updatedHotelOwner.getGender());
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
	public void deleteHotelOwner(Long hotelOwnerId) throws HotelOwnerNotFoundException {
		LOGGER.info("Deleting hotel owner with ID: {}", hotelOwnerId);
		HotelOwner hotelOwnerToDelete = hotelOwnerRepository.findById(hotelOwnerId)
				.orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

		hotelOwnerRepository.delete(hotelOwnerToDelete);
		LOGGER.info("Hotel owner deleted successfully");

	}

	public HotelOwner getHotelOwnerByEmail(String email) {
		LOGGER.info("Finding " + email + " in database");
		return hotelOwnerRepository.findByEmail(email).orElse(null);
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
