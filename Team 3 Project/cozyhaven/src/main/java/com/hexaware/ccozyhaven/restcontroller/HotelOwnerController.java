package com.hexaware.ccozyhaven.restcontroller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


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
@RequestMapping("/cozyhaven-hotelowner")
public class HotelOwnerController {

	@Autowired
	private IHotelOwnerService hotelOwnerService;

	Logger log = LoggerFactory.getLogger(HotelOwnerServiceImp.class);

	@PostMapping(path = "/add", consumes = "application/json")
	public String addHotelOwnerWithHotel(@RequestBody HotelOwner hotelOwner) {

		log.info("In Restcontroller Before : " + hotelOwner);
		hotelOwnerService.addHotelOwnerWithHotel(hotelOwner);
		return "HotelOwner and Hotel added successfully";

	}

	@PutMapping("/update/{hotelOwnerId}")
	public HotelOwner updateHotelOwner(@PathVariable Long hotelOwnerId, @RequestBody HotelOwner updateHotelOwner)
			throws HotelOwnerNotFoundException {
		HotelOwner updatedHotelOwner = hotelOwnerService.updateHotelOwner(hotelOwnerId, updateHotelOwner);
		return updatedHotelOwner;
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
//	

}
