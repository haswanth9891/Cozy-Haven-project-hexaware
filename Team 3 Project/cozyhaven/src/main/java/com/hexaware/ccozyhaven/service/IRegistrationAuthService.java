package com.hexaware.ccozyhaven.service;

import com.hexaware.ccozyhaven.entities.UserInfo;

public interface IRegistrationAuthService {

	public boolean registerUser(UserInfo userInfo);
	
	public boolean registerAdministrator(UserInfo userInfo);
	
	public boolean registerHotelOwner(UserInfo userInfo);
}
