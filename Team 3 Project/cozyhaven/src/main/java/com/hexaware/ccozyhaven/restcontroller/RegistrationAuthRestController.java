package com.hexaware.ccozyhaven.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.LoginDTO;
import com.hexaware.ccozyhaven.entities.UserInfo;
import com.hexaware.ccozyhaven.service.CozyHavenService;
import com.hexaware.ccozyhaven.service.JwtService;


@RestController
@RequestMapping("/api/register")
public class RegistrationAuthRestController {

	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CozyHavenService cozyHavenService;
	
	Logger logger=LoggerFactory.getLogger(RegistrationAuthRestController.class);

	
	@PostMapping("/user")
	public String registerUser(@RequestBody UserInfo userInfo) {
		logger.info("Hitting API to register user info for job seeker");
		return cozyHavenService.addUser(userInfo);
	}

	@PostMapping("/employer")
	public String registerEmployer(@RequestBody UserInfo employerInfo) {
		logger.info("Hitting API to register user info for employr");
		return cozyHavenService.addUser(employerInfo);
	}

	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody LoginDTO authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
		
		String token = null;
		
		if(authentication.isAuthenticated()) {
			logger.info("Generating jwt auth token");
			token = jwtService.generateToken(authRequest.getUsername());
		}else {
			throw new UsernameNotFoundException("Username or Password is invalid");
		}
		return token;
	}
}