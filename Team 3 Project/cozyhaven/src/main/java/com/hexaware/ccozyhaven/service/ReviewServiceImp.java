

package com.hexaware.ccozyhaven.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImp.class);

	@Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public void addReviewWithUserAndHotel(ReviewDTO reviewDTO, Long userId, Long hotelId)
            throws UserNotFoundException, HotelNotFoundException {
    	 LOGGER.info("Adding review with user ID {} and hotel ID {}", userId, hotelId);
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
        LOGGER.info("Review added successfully");
    }

	
	
	 @Override
	    public Review getReviewById(Long reviewId) throws ReviewNotFoundException {
		 LOGGER.info("Fetching review with ID {}", reviewId);
	        return reviewRepository.findById(reviewId)
	                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + reviewId));
	    }

	
	 @Override
	    public void updateReviewById(Long reviewId, ReviewDTO reviewDTO) throws ReviewNotFoundException {
		 LOGGER.info("Updating review with ID {}", reviewId);
	        Review existingReview = getReviewById(reviewId);

	       
	        existingReview.setRating(reviewDTO.getRating());
	        existingReview.setReviewText(reviewDTO.getReviewText());
	        existingReview.setReviewDate(reviewDTO.getReviewDate());

	        reviewRepository.save(existingReview);
	        LOGGER.info("Review updated successfully");
	    }
	 
	 @Override
	    public void deleteReviewById(Long reviewId) throws ReviewNotFoundException {
		 LOGGER.info("Deleting review with ID {}", reviewId);
	        Review reviewToDelete = getReviewById(reviewId);
	        reviewRepository.delete(reviewToDelete);
	        LOGGER.info("Review deleted successfully");
	    }

	 @Override
	    public List<Review> getAllReviews() {
		 LOGGER.info("Fetching all reviews");
	        return reviewRepository.findAll();
	    }

	 @Override
	    public List<Review> getAllReviewsForHotel(Long hotelId) throws HotelNotFoundException {
		 LOGGER.info("Fetching all reviews for hotel with ID {}", hotelId);
	        Hotel hotel = hotelRepository.findById(hotelId)
	                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + hotelId));

	        return reviewRepository.findAllByHotel(hotel);
	    }
	 @Override
	    public List<Review> getAllReviewsByUser(Long userId) throws UserNotFoundException {
		 LOGGER.info("Fetching all reviews for user with ID {}", userId);
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

	        return reviewRepository.findAllByUser(user);
	    }

    
	
	


}