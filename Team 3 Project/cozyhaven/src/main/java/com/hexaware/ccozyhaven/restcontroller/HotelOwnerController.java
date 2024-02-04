package com.hexaware.ccozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;
import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.service.IHotelOwnerService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/cozyhaven-hotelowner")
public class HotelOwnerController {
	
	 @Autowired
	    private IHotelOwnerService hotelOwnerService;
	 
	 
	 @PostMapping("/addrooms")
	    public ResponseEntity<Room> addRoom(@RequestBody RoomDTO roomDTO) {
	        Room addedRoom = hotelOwnerService.addRoom(roomDTO);
	        return new ResponseEntity<>(addedRoom, HttpStatus.CREATED);
	    }
	 
	 @PutMapping("/updateHotelOwner/{hotelOwnerId}")
	    public ResponseEntity<HotelOwner> updateHotelOwner(@PathVariable Long hotelOwnerId, @RequestBody HotelOwnerDTO updatedHotelOwnerDTO)
	            throws HotelOwnerNotFoundException {
	        HotelOwner updatedHotelOwner = hotelOwnerService.updateHotelOwner(hotelOwnerId, updatedHotelOwnerDTO);
	        return new ResponseEntity<>(updatedHotelOwner, HttpStatus.OK);
	    }
	 
	 @PutMapping("/editRoom/{roomId}")
	    public ResponseEntity<Room> editRoom(@PathVariable Long roomId, @RequestBody RoomDTO updatedRoomDTO)
	            throws RoomNotFoundException {
	        Room editedRoom = hotelOwnerService.editRoom(roomId, updatedRoomDTO);
	        return new ResponseEntity<>(editedRoom, HttpStatus.OK);
	    }
	 
	 @DeleteMapping("/removeRoom/{roomId}")
	    public ResponseEntity<String> removeRoom(@PathVariable Long roomId) throws RoomNotFoundException {
	        hotelOwnerService.removeRoom(roomId);
	        return new ResponseEntity<>("Room removed successfully", HttpStatus.OK);
	    }
	 
	 @GetMapping("/viewReservation/{hotelId}")
	    public ResponseEntity<List<Reservation>> viewReservation(@PathVariable Long hotelId) {
	        List<Reservation> reservations = hotelOwnerService.viewReservation(hotelId);
	        return new ResponseEntity<>(reservations, HttpStatus.OK);
	    }
	 
	 
	 @GetMapping("/refundAmount/{reservationId}")
	    public ResponseEntity<Double> refundAmount(@PathVariable Long reservationId)
	            throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
	        Double refundedAmount = hotelOwnerService.refundAmount(reservationId);
	        return new ResponseEntity<>(refundedAmount, HttpStatus.OK);
	    }

}
