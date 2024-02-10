package com.hexaware.ccozyhaven.service;






import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;

import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;







public interface IHotelOwnerService {
	
	
	boolean registerHotelOwner(HotelOwnerDTO hotelOwnerDTO) throws DataAlreadyPresentException, DataAlreadyPresentException;



	boolean login(String username, String password);
	


	void deleteHotelOwner(Long hotelOwnerId) throws HotelOwnerNotFoundException, AuthorizationException, UnauthorizedAccessException;

	

	

	void updateHotelOwnerWithHotel(Long hotelOwnerId, HotelOwnerDTO updatedHotelOwnerDTO)
			throws HotelOwnerNotFoundException, AuthorizationException, UnauthorizedAccessException;



	

	

	

	
}