package com.hexaware.ccozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.ReviewDTO;
import com.hexaware.ccozyhaven.entities.Review;
import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;
import com.hexaware.ccozyhaven.exceptions.ReviewNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.service.IReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cozyhaven/review")
public class ReviewController {
	
	 @Autowired
	    private IReviewService reviewService;

	 @PostMapping("/add/{userId}/{hotelId}")
	    public ResponseEntity<String> addReviewWithUserAndHotel(@RequestBody ReviewDTO reviewDTO,
	                                                            @PathVariable Long userId,
	                                                            @PathVariable Long hotelId) {
	        try {
	            reviewService.addReviewWithUserAndHotel(reviewDTO, userId, hotelId);
	            return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
	        } catch (UserNotFoundException | HotelNotFoundException e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
	    }

	 @GetMapping("/get/{reviewId}")
	    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
	        try {
	            Review review = reviewService.getReviewById(reviewId);
	            return new ResponseEntity<>(review, HttpStatus.OK);
	        } catch (ReviewNotFoundException e) {
	            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	        }
	    }
      
	 
	 @PutMapping("/update/{reviewId}")
	    public ResponseEntity<String> updateReviewById(@PathVariable Long reviewId,
	                                                   @RequestBody @Valid ReviewDTO reviewDTO) {
	        try {
	            reviewService.updateReviewById(reviewId, reviewDTO);
	            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
	        } catch (ReviewNotFoundException e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
	    }
	 
	 @DeleteMapping("/delete/{reviewId}")
	    public ResponseEntity<String> deleteReviewById(@PathVariable Long reviewId) {
	        try {
	            reviewService.deleteReviewById(reviewId);
	            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
	        } catch (ReviewNotFoundException e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
	    }
	 
	 
	 @GetMapping("/getall")
	    public ResponseEntity<List<Review>> getAllReviews() {
	        List<Review> reviews = reviewService.getAllReviews();
	        return new ResponseEntity<>(reviews, HttpStatus.OK);
	    }
	
	 
	 @GetMapping("/allreviews/hotel/{hotelId}")
	    public ResponseEntity<List<Review>> getAllReviewsForHotel(@PathVariable Long hotelId) {
	        try {
	            List<Review> reviews = reviewService.getAllReviewsForHotel(hotelId);
	            return new ResponseEntity<>(reviews, HttpStatus.OK);
	        } catch (HotelNotFoundException e) {
	            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	        }
	    }
	 
	 @GetMapping("/allreviews/user/{userId}")
	    public ResponseEntity<List<Review>> getAllReviewsByUser(@PathVariable Long userId) {
	        try {
	            List<Review> reviews = reviewService.getAllReviewsByUser(userId);
	            return new ResponseEntity<>(reviews, HttpStatus.OK);
	        } catch (UserNotFoundException e) {
	            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	        }
	    }
	 
}