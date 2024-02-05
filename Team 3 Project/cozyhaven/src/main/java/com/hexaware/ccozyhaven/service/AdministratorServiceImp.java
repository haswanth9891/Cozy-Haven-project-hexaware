package com.hexaware.ccozyhaven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdministratorServiceImp implements IAdministratorService{
	
	 @Autowired
	 private UserRepository userRepository;

	    @Autowired
	    private HotelOwnerRepository hotelOwnerRepository;
	   
	    @Autowired
	    private ReservationRepository reservationRepository;


	@Override
	public void deleteUserAccount(Long userId) throws UserNotFoundException {
		  User user = userRepository.findById(userId)
		            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

		   userRepository.delete(user);
	}

	@Override
	public void deleteHotelOwnerAccount(Long hotelOwnerId) throws UserNotFoundException {
		HotelOwner hotelOwner = hotelOwnerRepository.findById(hotelOwnerId)
	            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + hotelOwnerId));

	   hotelOwnerRepository.delete(hotelOwner);
		
	}

	@Override
	public List<User> viewAllUser() {
		
		return userRepository.findAll();
	}

	@Override
	public List<HotelOwner> viewAllHotelOwner() {
		
		return hotelOwnerRepository.findAll();
	}

	@Override
	public void manageRoomReservation(Long reservationId, String reservationStatus) throws ReservationNotFoundException, InvalidCancellationException {
		 Reservation reservation = reservationRepository.findById(reservationId)
	                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

	       
	        if (reservation.getReservationStatus() !="CANCELLED") {
	            
	            reservationRepository.delete(reservation);
	        } else {
	            throw new InvalidCancellationException("Reservation is already cancelled.");
	        }
	}

}
