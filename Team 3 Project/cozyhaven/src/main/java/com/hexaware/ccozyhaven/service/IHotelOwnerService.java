package com.hexaware.ccozyhaven.service;

import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;



public interface IHotelOwnerService {
	
	// register
	Long registerHotelOwner(HotelOwnerDTO hotelOwnerDTO) throws DataAlreadyPresentException, DataAlreadyPresentException;


    // login
	boolean login(String username, String password);
	

   // deleteHotelOwner
	String deleteHotelOwner(Long hotelOwnerId) throws HotelOwnerNotFoundException, AuthorizationException, UnauthorizedAccessException;

	//updateHotelOwnerwith HOtel
	void updateHotelOwnerWithHotel(Long hotelOwnerId, HotelOwnerDTO updatedHotelOwnerDTO)
			throws HotelOwnerNotFoundException, AuthorizationException, UnauthorizedAccessException;



	

	

	

	
}