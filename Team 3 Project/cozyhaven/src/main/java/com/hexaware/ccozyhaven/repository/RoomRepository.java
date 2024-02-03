package com.hexaware.ccozyhaven.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.ccozyhaven.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	
	@Query(value = "SELECT * FROM room_details r " +
		       "WHERE r.hotel_id IN (" +
		       "  SELECT h.hotel_id FROM hotel_details h " +
		       "  WHERE h.location = :location" +
		       ") " +
		       "AND r.availability_status = 'AVAILABLE' " +
		       "AND NOT EXISTS (" +
		       "  SELECT res.reservation_id FROM reservation_details res " +
		       "  JOIN room_details resRoom ON res.reservation_id = resRoom.reservation_id " +
		       "  WHERE (resRoom.room_id = r.room_id) " +
		       "  AND (" +
		       "    (res.check_in_date BETWEEN :checkInDate AND :checkOutDate) " +
		       "    OR (res.check_out_date BETWEEN :checkInDate AND :checkOutDate)" +
		       "  )" +
		       ") " +
		       "ORDER BY r.base_fare ASC " +
		       "LIMIT :numberOfRooms",
		       nativeQuery = true)
		List<Room> findAvailableRooms(@Param("location") String location,
		                              @Param("checkInDate") LocalDate checkInDate,
		                              @Param("checkOutDate") LocalDate checkOutDate,
		                              @Param("numberOfRooms") int numberOfRooms);
	

}
