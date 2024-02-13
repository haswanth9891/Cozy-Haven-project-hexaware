package com.hexaware.ccozyhaven.service;

import com.hexaware.ccozyhaven.dto.PaymentDTO;
import com.hexaware.ccozyhaven.entities.Payment;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

public interface IPaymentService {
	
	public Payment processPayment(PaymentDTO paymentDTO) throws ReservationNotFoundException, UserNotFoundException;
	
	

}
