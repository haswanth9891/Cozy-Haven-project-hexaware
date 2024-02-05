package com.hexaware.ccozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.Review;

@Repository
public interface ReviewRepository  extends JpaRepository<Review, Long>{

}
