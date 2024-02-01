package com.hexaware.ccozyhaven.service;

import org.hibernate.mapping.List;



public class AdministratorServiceImpl implements IAdministratorService {

	@Override
	public boolean loginAdministrator(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUserAccount(Long userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteHotelOwnerAccount(Long hotelOwnerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List viewAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List viewAllHotelOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean manageRoomReservation(Long reservationId, String reservationStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}