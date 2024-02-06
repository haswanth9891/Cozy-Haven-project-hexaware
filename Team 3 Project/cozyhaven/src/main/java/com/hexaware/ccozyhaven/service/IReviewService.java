package com.hexaware.ccozyhaven.service;

import java.util.List;

import com.hexaware.ccozyhaven.dto.ReviewDTO;
import com.hexaware.ccozyhaven.entities.Review;
import com.hexaware.ccozyhaven.exceptions.ReviewNotFoundException;

public interface IReviewService {

	void addReview(ReviewDTO reviewDTO);

	Review getReviewById(Long reviewId) throws ReviewNotFoundException;

	void updateReview(ReviewDTO reviewDTO) throws ReviewNotFoundException;

	void deleteReview(Long reviewId) throws ReviewNotFoundException;

	List<Review> getAllReviews();

//    // Get all reviews for a specific hotel
//    List<Review> getAllReviewsForHotel(Long hotelId);
//
//    // Get all reviews by a specific user
//    List<Review> getAllReviewsByUser(Long userId);

	// Get all reviews

}
