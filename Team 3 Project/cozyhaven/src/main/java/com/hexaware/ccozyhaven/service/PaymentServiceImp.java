package com.hexaware.ccozyhaven.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
@Transactional
public class PaymentServiceImp implements IPaymentService{
	
	private final PaymentRepository paymentRepository;
	private final UserRepository userRepository;
	private final ReservationRepository reservationRepository;
	
    @Autowired
    public PaymentServiceImp(PaymentRepository paymentRepository, UserRepository userRepository, ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }
   
    
    
    @Override
    public Payment processPayment( PaymentDTO paymentDTO) throws ReservationNotFoundException, UserNotFoundException {
        // In a real-world scenario, this method would interact with a payment gateway API
        // Here, we simulate a successful payment by saving the payment details to the database

        // You may use a payment gateway SDK or API for real payment processing

        // Simulate a successful payment
    	 User user = userRepository.findById(paymentDTO.getUserId())
 	            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + paymentDTO.getUserId()));

 	    Reservation reservation = reservationRepository.findById(paymentDTO.getReservationId())
 	            .orElseThrow(() -> new ReservationNotFoundException("Cart not found with id: " + paymentDTO.getReservationId()));

 	   reservation.setReservationStatus("CONFIRMED");
 	    Payment payment = new Payment();
 	    payment.setUser(user);
 	    payment.setReservation(reservation);
 	    payment.setPaymentDate(LocalDateTime.now());
 	    payment.setAmount(reservation.getTotalAmount());
 	    payment.setPaymentMethod(paymentDTO.getPaymentMethod());
 	    payment.setTransactionID(generateRandomTransactionID());
 	    payment.setStatus("SUCCESS");
 	    
 	    reservation.setReservationStatus("CONFIRMED");
 	    reservation.setPayment(payment);
        
        return paymentRepository.save(payment);
    }
    
    private String generateRandomTransactionID() {
        Random random = new Random();
        int transactionIDLength = 10;
        StringBuilder sb = new StringBuilder(transactionIDLength);
        for (int i = 0; i < transactionIDLength; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}
