package com.hexaware.ccozyhaven.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	private AdministratorRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HotelOwnerRepository hotelOwnerRepository;

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Override
	public String login(String username, String password) {
		String token = null;
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		if (authentication.isAuthenticated()) {
			token = jwtService.generateToken(username);
			if (token != null) {
				LOGGER.info("Token Generated for Admin: " + token);
			} else {
				LOGGER.warn("Token not generated");
			}
		} else {
			throw new UsernameNotFoundException("Username not found");
		}
		return token;
		
		
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
		admin.setPassword(new BCryptPasswordEncoder().encode(adminDto.getPassword()));
		Administrator savedAdmin = adminRepository.save(admin);
		LOGGER.info("Admin Created: " + savedAdmin);
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
