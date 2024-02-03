package com.hexaware.ccozyhaven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.ccozyhaven.entities.Hotel;

import com.hexaware.ccozyhaven.entities.Room;

public interface HotelRepository extends JpaRepository<Hotel,Long>{
	
	Optional<Hotel> findByIdAndUserId(Long reservationId, Long userId);
	
	List<Room> findByHotelIdAndAvailabilityStatus(Long hotelId, boolean status);

}
