package com.hexaware.ccozyhaven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.RoomRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class HotelOwnerServiceImp implements IHotelOwnerService{
	
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	HotelOwnerRepository hotelOwnerRepository;

	
//	@Override
//	public HotelOwner registerHotelOwner(HotelOwnerDTO hotelOwnerDTO) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean loginHotelOwner(String username, String password) {
//		// TODO Auto-generated method stub
//		return false;
//	}
	

	@Override
	public Room addRoom(RoomDTO roomDTO) {
		Room newRoom = new Room();
        newRoom.setRoomSize(roomDTO.getRoomSize());
        newRoom.setBedType(roomDTO.getBedType());
        newRoom.setMaxOccupancy(roomDTO.getMaxOccupancy());
        newRoom.setBaseFare(roomDTO.getBaseFare());
        newRoom.setAC(roomDTO.isAC());
        newRoom.setAvailabilityStatus(roomDTO.isAvailabilityStatus());

        // Save the new room to the database
        return roomRepository.save(newRoom);
	}

	@Override
	public HotelOwner updateHotelOwner(Long hotelOwnerId, HotelOwnerDTO updatedHotelOwnerDTO) throws HotelOwnerNotFoundException {
		
		HotelOwner existingHotelOwner = hotelOwnerRepository.findById(hotelOwnerId)
                .orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

        
        existingHotelOwner.setHotelOwnerName(updatedHotelOwnerDTO.getHotelOwnerName());
       
        existingHotelOwner.setEmail(updatedHotelOwnerDTO.getEmail());
       
        existingHotelOwner.setHotel(updatedHotelOwnerDTO.getHotel());
      
        return hotelOwnerRepository.save(existingHotelOwner);
		
	}

	

	@Override
	public Room editRoom(Long roomId, RoomDTO updatedRoomDTO) throws RoomNotFoundException {
		 Room existingRoom = roomRepository.findById(roomId)
	                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

	       
	        existingRoom.setRoomSize(updatedRoomDTO.getRoomSize());
	        existingRoom.setBedType(updatedRoomDTO.getBedType());
	        existingRoom.setMaxOccupancy(updatedRoomDTO.getMaxOccupancy());
	        existingRoom.setBaseFare(updatedRoomDTO.getBaseFare());
	        existingRoom.setAC(updatedRoomDTO.isAC());
	        existingRoom.setAvailabilityStatus(updatedRoomDTO.isAvailabilityStatus());

	       
	        return roomRepository.save(existingRoom);
	}

	@Override
	public void removeRoom(Long roomId) throws RoomNotFoundException {
		Room roomToDelete = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

       
        roomRepository.delete(roomToDelete);
	}

	@Override
	public List<Reservation> viewReservation(Long hotelId) {
		
		return reservationRepository.findByUser_UserId(hotelId);
	}

	@Override
	public double refundAmount(Long reservationId) throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
		Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

        // Check if the reservation is canceled
        if (reservation.getReservationStatus() == "CANCELLED") {
            // Check if a refund has not been processed
            if (!reservation.isRefundProcessed()) {
                // Perform the refund logic here (e.g., process payment, etc.)

                // Calculate and return the refunded amount (dummy value, replace with actual calculation)
                double refundedAmount = calculateRefundedAmount(reservation);
                
                // Optionally, update the reservation to mark refund as processed
                reservation.setRefundProcessed(true);
                reservationRepository.save(reservation);

                return refundedAmount;
            } else {
                throw new RefundProcessedException("Refund already processed for reservationId: " + reservationId);
            }
        } else {
            throw new InvalidRefundException("Refund can only be processed for canceled reservations.");
        }
	}
	
	 private double calculateRefundedAmount(Reservation reservation) {
	        
	        return reservation.getTotalAmount() * 0.8;
	 }




}
