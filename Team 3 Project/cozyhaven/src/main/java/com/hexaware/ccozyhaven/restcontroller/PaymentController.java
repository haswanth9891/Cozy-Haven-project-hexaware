package com.hexaware.ccozyhaven.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.PaymentDTO;
import com.hexaware.ccozyhaven.entities.Payment;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

import com.hexaware.ccozyhaven.service.IPaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	 private final IPaymentService paymentService;
	  

	    @Autowired
	    public PaymentController(IPaymentService paymentService) {
	        this.paymentService = paymentService;
	        
	    }

	    @PostMapping("/make-payment")
	    @PreAuthorize("hasAuthority('USER')")
	    public ResponseEntity<Payment> processPayment(@RequestBody PaymentDTO paymentDTO)
	            throws ReservationNotFoundException, UserNotFoundException {
	       
	        Payment processedPayment = paymentService.processPayment(paymentDTO);

	        return new ResponseEntity<>(processedPayment, HttpStatus.OK);
	    }

}
