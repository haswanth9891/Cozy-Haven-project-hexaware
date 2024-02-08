package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

@SpringBootTest
@Transactional
class ReviewServiceImpTest {
	
	@Autowired
    private ReviewServiceImp reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;
	
 // Test Case 1: Add review by user Id and hotel Id
    @Test
    @Disabled
    void testAddReviewWithUserAndHotel() throws UserNotFoundException, HotelNotFoundException {
        
        ReviewDTO reviewDTO = new ReviewDTO(null, 5, "Excellent", new Date());

        User user = new User();
        user.setUserName("john_doe_user");
        user.setEmail("john.doe@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setContactNumber("1234567890");
        user.setGender("male");
        user.setAddress("123 Main St");
        user.setPassword("john@123"); // Add the desired password here
        userRepository.save(user);
        
       
        Hotel hotel = new Hotel();
        hotel.setHotelName("Hotel ABC");
        hotel.setLocation("Location XYZ");
        hotel.setHasDining(true);
        hotel.setHasParking(true);
        hotel.setHasFreeWiFi(true);
        hotel.setHasRoomService(true);
        hotel.setHasSwimmingPool(true);
        hotel.setHasFitnessCenter(true);

        // Saving user and hotel
        userRepository.save(user);
        hotelRepository.save(hotel);

       
        assertDoesNotThrow(() -> reviewService.addReviewWithUserAndHotel(reviewDTO, user.getUserId(), hotel.getHotelId()));

       
        List<Review> reviews = reviewService.getAllReviews();
        assertEquals(1, reviews.size());
        assertEquals("Excellent", reviews.get(0).getReviewText());
        assertNotNull(reviews.get(0).getReviewId());
        assertNotNull(reviews.get(0).getUser());
        assertNotNull(reviews.get(0).getHotel());
    }


    @Test
    void testGetReviewById() throws ReviewNotFoundException {
       
        Long existingReviewId = 2L;

        // Act
        Review resultReview = reviewService.getReviewById(existingReviewId);

       
        assertEquals(existingReviewId, resultReview.getReviewId());
       
    }
    
    @Test
    void testUpdateReviewById() throws ReviewNotFoundException {
        
        Long existingReviewId = 2L;
        ReviewDTO updatedReviewDTO = new ReviewDTO(null, 4, "Good", new Date());

       
        reviewService.updateReviewById(existingReviewId, updatedReviewDTO);

        
        Review updatedReview = reviewRepository.findById(existingReviewId)
                .orElseThrow(() -> new ReviewNotFoundException(null));

        assertEquals(updatedReviewDTO.getRating(), updatedReview.getRating());
        assertEquals(updatedReviewDTO.getReviewText(), updatedReview.getReviewText());
       
    }


	@Test
	void testDeleteReviewById() throws ReviewNotFoundException {
	    
	    Long existingReviewId = 3L;

	  
	    reviewService.deleteReviewById(existingReviewId);

	    //  Validate that the review was deleted
	    assertThrows(ReviewNotFoundException.class, () -> reviewService.getReviewById(existingReviewId));
	}
	
	@Test
	void testGetAllReviews() {
	    // Arrange: No specific arrangement needed for this test case

	    // Act
	    List<Review> allReviews = reviewService.getAllReviews();

	    // Validate that the list is not null or empty
	    assertNotNull(allReviews);
	    assertFalse(allReviews.isEmpty());
	   
	}

	@Test
	void testGetAllReviewsForHotel() throws HotelNotFoundException {
	    
	    Long existingHotelId = 1L;

	    // Act
	    List<Review> hotelReviews = reviewService.getAllReviewsForHotel(existingHotelId);

	    
	    assertNotNull(hotelReviews);
	    assertFalse(hotelReviews.isEmpty());
	  
	}

	@Test
	void testGetAllReviewsByUser() throws UserNotFoundException {
	   
	    Long existingUserId = 1L;

	
	    List<Review> userReviews = reviewService.getAllReviewsByUser(existingUserId);

	   
	    assertNotNull(userReviews);
	    assertFalse(userReviews.isEmpty());
	   
	}
}
