package com.hexaware.ccozyhaven.service;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;
import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.RoomRepository;

import jakarta.transaction.Transactional;



@Service
@Transactional
public class HotelOwnerServiceImp implements IHotelOwnerService {
	
	@Autowired
	HotelOwnerRepository hotelOwnerRepo;
	
	@Autowired
	RoomRepository roomRepo;
	
	

	@Override
	public boolean loginHotelOwner(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public boolean removeRoom(Long roomId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Reservation> viewReservation(Long hotelId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double refundAmount(Long reservationId) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public HotelOwner registerHotelOwner(HotelOwnerDTO hotelOwnerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HotelOwner updateHotelOwner(Long hotelOwnerId, HotelOwnerDTO updatedHotelOwnerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room addRoom(RoomDTO roomDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room editRoom(Long roomId, RoomDTO updatedRoomDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
