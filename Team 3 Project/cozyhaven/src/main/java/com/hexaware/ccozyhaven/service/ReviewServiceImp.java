

package com.hexaware.ccozyhaven.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.ReviewDTO;
import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Review;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;
import com.hexaware.ccozyhaven.exceptions.ReviewNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.repository.HotelRepository;
import com.hexaware.ccozyhaven.repository.ReviewRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReviewServiceImp implements IReviewService {

	@Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public void addReviewWithUserAndHotel(ReviewDTO reviewDTO, Long userId, Long hotelId)
            throws UserNotFoundException, HotelNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + hotelId));

        Review newReview = new Review();
        newReview.setUser(user);
        newReview.setHotel(hotel);
        newReview.setRating(reviewDTO.getRating());
        newReview.setReviewText(reviewDTO.getReviewText());
        newReview.setReviewDate(reviewDTO.getReviewDate());

        reviewRepository.save(newReview);
    }

	
	
	 @Override
	    public Review getReviewById(Long reviewId) throws ReviewNotFoundException {
	        return reviewRepository.findById(reviewId)
	                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + reviewId));
	    }

	
	 @Override
	    public void updateReviewById(Long reviewId, ReviewDTO reviewDTO) throws ReviewNotFoundException {
	        Review existingReview = getReviewById(reviewId);

	       
	        existingReview.setRating(reviewDTO.getRating());
	        existingReview.setReviewText(reviewDTO.getReviewText());
	        existingReview.setReviewDate(reviewDTO.getReviewDate());

	        reviewRepository.save(existingReview);
	    }
	 @Override
	    public void deleteReviewById(Long reviewId) throws ReviewNotFoundException {
	        Review reviewToDelete = getReviewById(reviewId);
	        reviewRepository.delete(reviewToDelete);
	    }

	 @Override
	    public List<Review> getAllReviews() {
	        return reviewRepository.findAll();
	    }

	 @Override
	    public List<Review> getAllReviewsForHotel(Long hotelId) throws HotelNotFoundException {
	        Hotel hotel = hotelRepository.findById(hotelId)
	                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + hotelId));

	        return reviewRepository.findAllByHotel(hotel);
	    }
	 @Override
	    public List<Review> getAllReviewsByUser(Long userId) throws UserNotFoundException {
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

	        return reviewRepository.findAllByUser(user);
	    }

    
	
	


}