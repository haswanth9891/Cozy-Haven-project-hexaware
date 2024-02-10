package com.hexaware.ccozyhaven.restcontroller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;

import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;

import com.hexaware.ccozyhaven.service.HotelOwnerServiceImp;
import com.hexaware.ccozyhaven.service.IHotelOwnerService;

@RestController
@RequestMapping("/api/hotelowner")
public class HotelOwnerController {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(HotelOwnerController.class);

	 
	@Autowired
	private IHotelOwnerService hotelOwnerService;

	

	@PostMapping(path = "/add", consumes = "application/json")
	public String addHotelOwnerWithHotel(@RequestBody HotelOwnerDTO hotelOwnerDTO) {

		LOGGER.info("Received request to add HotelOwner with Hotel: {}", hotelOwnerDTO);
		hotelOwnerService.addHotelOwnerWithHotel(hotelOwnerDTO);
		return "HotelOwner and Hotel added successfully";

	}

	 @PutMapping("/update/{hotelOwnerId}")
	    public ResponseEntity<String> updateHotelOwner(@PathVariable Long hotelOwnerId,
	                                                   @RequestBody HotelOwnerDTO updatedHotelOwnerDTO) {
	        try {
	            hotelOwnerService.updateHotelOwnerWithHotel(hotelOwnerId, updatedHotelOwnerDTO);
	            return new ResponseEntity<>("HotelOwner updated successfully", HttpStatus.OK);
	        } catch (HotelOwnerNotFoundException e) {
	            LOGGER.error("Error updating HotelOwner with ID: {}", hotelOwnerId, e);
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
	    }
	
	@DeleteMapping("/delete/{hotelOwnerId}")
    public ResponseEntity<String> deleteHotelOwner(@PathVariable Long hotelOwnerId) {
        try {
            hotelOwnerService.deleteHotelOwner(hotelOwnerId);
            return new ResponseEntity<>("Hotel owner deleted successfully", HttpStatus.OK);
        } catch (HotelOwnerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
	
	
	
	
	
	
	
	
	

//	 @PostMapping("/{hotelOwnerId}/addhotel")
//	    public ResponseEntity<Hotel> addHotel(@PathVariable Long hotelOwnerId, @RequestBody HotelDTO hotelDTO) {
//	        try {
//	            Hotel addedHotel = hotelOwnerService.addHotel(hotelOwnerId, hotelDTO);
//	            return new ResponseEntity<>(addedHotel, HttpStatus.CREATED);
//	        } catch (HotelOwnerNotFoundException e) {
//	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	        }
//	    }


}
