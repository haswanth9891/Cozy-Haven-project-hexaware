package com.hexaware.ccozyhaven.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.ReviewDTO;
import com.hexaware.ccozyhaven.entities.Review;
import com.hexaware.ccozyhaven.exceptions.ReviewNotFoundException;
import com.hexaware.ccozyhaven.repository.ReviewRepository;

@Service
public class ReviewServiceImp implements IReviewService {

	@Autowired
	ReviewRepository reviewRepository;

	@Override
	public void addReview(ReviewDTO reviewDTO) {
		Review review = new Review();
		// Map fields manually
		review.setRating(reviewDTO.getRating());
		review.setReviewText(reviewDTO.getReviewText());
		review.setReviewDate(reviewDTO.getReviewDate());

		reviewRepository.save(review);

	}

	@Override
	public Review getReviewById(Long reviewId) throws ReviewNotFoundException {
		Optional<Review> optionalReview = reviewRepository.findById(reviewId);
		return optionalReview.orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + reviewId));

	}

	@Override
	public void updateReview(ReviewDTO reviewDTO) throws ReviewNotFoundException {
		Long reviewId = reviewDTO.getReviewId();
		Review existingReview = getReviewById(reviewId);

		existingReview.setRating(reviewDTO.getRating());
		existingReview.setReviewText(reviewDTO.getReviewText());
		existingReview.setReviewDate(reviewDTO.getReviewDate());

		reviewRepository.save(existingReview);

	}

	@Override
	public void deleteReview(Long reviewId) throws ReviewNotFoundException {
		Review existingReview = getReviewById(reviewId);
		reviewRepository.delete(existingReview);

	}
//
//	@Override
//	public List<Review> getAllReviewsForHotel(Long hotelId) {
//		return reviewRepository.findByHotel_HotelId(hotelId);
//
//	}
//
//	@Override
//	public List<Review> getAllReviewsByUser(Long userId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<Review> getAllReviews() {
		return reviewRepository.findAll();

	}

}
