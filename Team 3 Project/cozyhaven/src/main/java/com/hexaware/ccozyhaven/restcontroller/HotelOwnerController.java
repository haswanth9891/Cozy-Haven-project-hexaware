package com.hexaware.ccozyhaven.restcontroller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;
import com.hexaware.ccozyhaven.dto.LoginDTO;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;
import com.hexaware.ccozyhaven.service.HotelOwnerServiceImp;
import com.hexaware.ccozyhaven.service.IHotelOwnerService;
import com.hexaware.ccozyhaven.service.JwtService;

@RestController
@RequestMapping("/api/hotelowner")
public class HotelOwnerController {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(HotelOwnerController.class);

	 
	@Autowired
	private IHotelOwnerService hotelOwnerService;
	
	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	

	@PostMapping("/register")
	public boolean registerCustomer(@RequestBody HotelOwnerDTO hotelOwnerDTO) throws DataAlreadyPresentException {
		LOGGER.info("Request Received to register new Hotel Owner: "+ hotelOwnerDTO);
		return hotelOwnerService.registerHotelOwner(hotelOwnerDTO);
	}
	
	@PostMapping("/login")
	public String authenticateAndGetToken(@RequestBody LoginDTO loginDto) {
		String token = null;
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
		if(authentication.isAuthenticated()) {
			token= jwtService.generateToken(loginDto.getUsername());
			if(token != null) {
				LOGGER.info("Token for Hotel Owner: "+token);
			}else {
				LOGGER.warn("Token not generated");
			}
		}else {
			throw new UsernameNotFoundException("Username not found");
		}
		return token;
	}

	 @PutMapping("/update/{hotelOwnerId}")
	 @PreAuthorize("hasAuthority('HOTEL OWNER')")
	    public ResponseEntity<String> updateHotelOwner(@PathVariable Long hotelOwnerId,
	                                                   @RequestBody HotelOwnerDTO updatedHotelOwnerDTO) throws AuthorizationException, UnauthorizedAccessException {
	        try {
	            hotelOwnerService.updateHotelOwnerWithHotel(hotelOwnerId, updatedHotelOwnerDTO);
	            return new ResponseEntity<>("HotelOwner updated successfully", HttpStatus.OK);
	        } catch (HotelOwnerNotFoundException e) {
	            LOGGER.error("Error updating HotelOwner with ID: {}", hotelOwnerId, e);
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
	    }
	
	@DeleteMapping("/delete/{hotelOwnerId}")
	@PreAuthorize("hasAuthority('HOTEL OWNER')")
    public ResponseEntity<String> deleteHotelOwner(@PathVariable Long hotelOwnerId) throws AuthorizationException, UnauthorizedAccessException {
        try {
            hotelOwnerService.deleteHotelOwner(hotelOwnerId);
            return new ResponseEntity<>("Hotel owner deleted successfully", HttpStatus.OK);
        } catch (HotelOwnerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
	
	
	
	
	
	
	
	
	



}
