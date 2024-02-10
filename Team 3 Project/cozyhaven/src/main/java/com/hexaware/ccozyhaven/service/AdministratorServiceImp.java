package com.hexaware.ccozyhaven.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.AdministratorDTO;
import com.hexaware.ccozyhaven.entities.Administrator;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.DataAlreadyPresentException;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.repository.AdministratorRepository;
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdministratorServiceImp implements IAdministratorService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorServiceImp.class);
	
	@Autowired
	private AdministratorRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HotelOwnerRepository hotelOwnerRepository;

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register(AdministratorDTO adminDto) throws DataAlreadyPresentException {
		Administrator localAdmin = adminRepository.findByEmail(adminDto.getEmail()).orElse(null);
		if(localAdmin!=null) {
			throw new DataAlreadyPresentException("Email Id already present");
		}
		Administrator admin = new Administrator();
		admin.setAdminFirstName(adminDto.getAdminFirstName());
		admin.setAdminLastName(adminDto.getAdminLastName());
		admin.setEmail(adminDto.getEmail());
		admin.setPassword(adminDto.getPassword());
		admin.setRole("Admin");
		Administrator savedAdmin = adminRepository.save(admin);
		LOGGER.info("Saved Admin: "+savedAdmin);
		return true;
	}

	@Override
	public void deleteUserAccount(Long userId) throws UserNotFoundException {
		 LOGGER.info("Deleting user account with ID: {}", userId);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

		userRepository.delete(user);
		 LOGGER.info("User account deleted successfully");
	}

	@Override
	public void deleteHotelOwnerAccount(Long hotelOwnerId) throws UserNotFoundException {
		LOGGER.info("Deleting hotel owner account with ID: {}", hotelOwnerId);
		HotelOwner hotelOwner = hotelOwnerRepository.findById(hotelOwnerId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + hotelOwnerId));

		hotelOwnerRepository.delete(hotelOwner);
		 LOGGER.info("Hotel owner account deleted successfully");
    

	}

	@Override
	public List<User> viewAllUser() {
		LOGGER.info("Viewing all users");

		return userRepository.findAll();
	}

	@Override
	public List<HotelOwner> viewAllHotelOwner() {
		 LOGGER.info("Viewing all hotel owners");

		return hotelOwnerRepository.findAll();
	}

	@Override
	public void manageRoomReservation(Long reservationId, String reservationStatus)
			throws ReservationNotFoundException, InvalidCancellationException {
		LOGGER.info("Managing room reservation with ID: {}", reservationId);
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

		if (reservation.getReservationStatus()!= "CANCELLED") {

			reservationRepository.delete(reservation);
		} else {
			 LOGGER.warn("Invalid cancellation request for already cancelled reservation");
			throw new InvalidCancellationException("Reservation is already cancelled.");
		}
	}

}
