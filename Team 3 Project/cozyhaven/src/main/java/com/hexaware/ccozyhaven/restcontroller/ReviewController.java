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
import com.hexaware.ccozyhaven.exceptions.ReviewNotFoundException;
import com.hexaware.ccozyhaven.service.IReviewService;

@RestController
@RequestMapping("/cozyhaven-review")
public class ReviewController {
	
	 @Autowired
	    private IReviewService reviewService;

	    @PostMapping("/add")
	    public String addReview(@RequestBody ReviewDTO reviewDTO) {
	        reviewService.addReview(reviewDTO);
	        return "Review added successfully";
	    }

	    @GetMapping("/get/{reviewId}")
	    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
	        try {
	            Review review = reviewService.getReviewById(reviewId);
	            return new ResponseEntity<>(review, HttpStatus.OK);
	        } catch (ReviewNotFoundException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @PutMapping("/update")
	    public ResponseEntity<String> updateReview(@RequestBody ReviewDTO reviewDTO) {
	        try {
	            reviewService.updateReview(reviewDTO);
	            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
	        } catch (ReviewNotFoundException e) {
	            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
	        }
	    }

	    @DeleteMapping("/delete/{reviewId}")
	    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
	        try {
	            reviewService.deleteReview(reviewId);
	            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
	        } catch (ReviewNotFoundException e) {
	            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
	        }
	    }

	    @GetMapping("/getall")
	    public ResponseEntity<List<Review>> getAllReviews() {
	        List<Review> reviews = reviewService.getAllReviews();
	        return new ResponseEntity<>(reviews, HttpStatus.OK);
	    }

}
