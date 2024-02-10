package com.hexaware.ccozyhaven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.hexaware.ccozyhaven.entities.UserInfo;
import com.hexaware.ccozyhaven.repository.AdministratorRepository;
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.UserInfoRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;



@Service
public class RegistrationServiceImp implements IRegistrationAuthService {

	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	HotelOwnerRepository hotelOwnerRepository;
	
	@Autowired
	AdministratorRepository administratorRepository;
	
	

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	Logger logger =LoggerFactory.getLogger(RegistrationServiceImp.class);
	
	@Override
	public boolean registerUser(UserInfo userInfo) {

		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		logger.info("Registering User");
		return userInfoRepository.save(userInfo) != null;
	}

	@Override
	public boolean registerHotelOwner(UserInfo hotelOwnerInfo) {

		hotelOwnerInfo.setPassword(passwordEncoder.encode(hotelOwnerInfo.getPassword()));
		logger.info("Registering Hotel Owner");
		return userInfoRepository.save(hotelOwnerInfo) != null;
	}
	
	@Override
	public boolean registerAdministrator(UserInfo AdministratorInfo) {

		AdministratorInfo.setPassword(passwordEncoder.encode(AdministratorInfo.getPassword()));
		logger.info("Registering Administrator");
		return userInfoRepository.save(AdministratorInfo) != null;
	}
	
	
	
}
