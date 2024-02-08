package com.hexaware.ccozyhaven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Review;
import com.hexaware.ccozyhaven.entities.User;

@Repository
public interface ReviewRepository  extends JpaRepository<Review, Long>{
	List<Review> findAllByHotel(Hotel hotel);
	List<Review> findAllByUser(User user);

	


}
