package com.hexaware.ccozyhaven.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.ccozyhaven.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	
	@Query("SELECT r FROM Room r " +
	           "WHERE r.hotel.location = :location " +
	           "AND r.availabilityStatus = 'AVAILABLE' " +
	           "AND NOT EXISTS (" +
	           "  SELECT res FROM Reservation res " +
	           "  JOIN res.rooms resRoom " +
	           "  WHERE (resRoom.roomId = r.roomId) " +
	           "  AND (" +
	           "    (res.checkInDate BETWEEN :checkInDate AND :checkOutDate) " +
	           "    OR (res.checkOutDate BETWEEN :checkInDate AND :checkOutDate)" +
	           "  )" +
	           ") " +
	           "ORDER BY r.baseFare ASC")
	    List<Room> findAvailableRooms(@Param("location") String location,
	                                  @Param("checkInDate") LocalDate checkInDate,
	                                  @Param("checkOutDate") LocalDate checkOutDate,
	                                  @Param("numberOfRooms") int numberOfRooms);

}
