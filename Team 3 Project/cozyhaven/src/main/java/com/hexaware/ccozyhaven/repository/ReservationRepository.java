package com.hexaware.ccozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.ccozyhaven.entities.Reservation;

public interface ReservationRepository extends JpaRepository <Reservation,Long>{

}
