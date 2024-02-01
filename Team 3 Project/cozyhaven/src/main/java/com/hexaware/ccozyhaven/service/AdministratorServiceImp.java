package com.hexaware.ccozyhaven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.AdministratorDTO;
import com.hexaware.ccozyhaven.entities.Administrator;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.repository.AdministratorRepository;
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdministratorServiceImp implements IAdministratorService{
	
	@Autowired
	AdministratorRepository adminRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	HotelOwnerRepository hotelOwnerRepo;

	@Override
	public Administrator registerAdministrator(AdministratorDTO adminDTO) {
		// TODO Auto-generated method stub
		return null;
	}

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
	public List<User> viewAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HotelOwner> viewAllHotelOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean manageRoomReservation(Long reservationId, String reservationStatus) {
		// TODO Auto-generated method stub
		return false;
	}

}
