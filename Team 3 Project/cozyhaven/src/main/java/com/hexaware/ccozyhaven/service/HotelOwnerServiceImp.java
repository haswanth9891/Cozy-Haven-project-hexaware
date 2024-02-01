package com.hexaware.ccozyhaven.service;


import org.hibernate.mapping.List;

import com.hexaware.ccozyhaven.entities.HotelOwner;




public class HotelOwnerServiceImp implements IHotelOwnerService {

	@Override
	public boolean loginHotelOwner(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addRoom(Long hotelId, com.hexaware.ccozyhaven.entities.Room roomDetails) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editRoom(Long roomId, com.hexaware.ccozyhaven.entities.Room updatedRoomDetails) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeRoom(Long roomId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List viewReservation(Long hotelId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean refundAmount(Long reservationId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerHotelOwner(HotelOwner hotelOwner) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
