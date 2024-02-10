package com.hexaware.ccozyhaven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.entities.UserInfo;
import com.hexaware.ccozyhaven.repository.UserInfoRepository;



@Service
public class CozyHavenService {
	@Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
	
    private static final Logger logger=LoggerFactory.getLogger(CozyHavenService.class); 
	
	public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        logger.info("User added to system");
        return "user added to system ";
    }
}
