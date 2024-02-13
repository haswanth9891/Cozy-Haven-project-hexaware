package com.hexaware.ccozyhaven.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.ccozyhaven.dto.PaymentDTO;
import com.hexaware.ccozyhaven.entities.Payment;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.repository.PaymentRepository;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class PaymentServiceImpTest {
	
	
	 @Autowired
	    private PaymentRepository paymentRepository;

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private ReservationRepository reservationRepository;

	    @Autowired
	    private PaymentServiceImp paymentService;
	
	

	@Test
	void testProcessPayment() throws UserNotFoundException, ReservationNotFoundException {
        // Given
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setUserId(1L);
        paymentDTO.setReservationId(1L);
        paymentDTO.setPaymentMethod("credit card");

        
        
        

       

        
        Payment result = paymentService.processPayment(paymentDTO);

        
        assertEquals("CONFIRMED", result.getReservation().getReservationStatus());
        assertEquals("SUCCESS", result.getStatus());
        // Additional assertions can be added based on your requirements
    }

   

}
