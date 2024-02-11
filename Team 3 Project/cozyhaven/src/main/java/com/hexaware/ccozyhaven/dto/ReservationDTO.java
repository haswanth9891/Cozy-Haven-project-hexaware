package com.hexaware.ccozyhaven.dto;

import java.util.Date;
/*
 * Author: Nafisa
 * 
 * DTO description: Represents the data transfer object for Reservation entity.
 * It contains fields relevant to Reservation, getter and setters, constructors, and relevant validations.
 */



public class ReservationDTO {
	private Long reservationId;
	private Date checkInDate;
	private Date checkOutDate;
	private int numberOfAdults;
	private int numberOfChildren;
	private double totalAmount;
	private String reservationStatus;
	
	
	public ReservationDTO() {
		super();
		
	}


	public ReservationDTO(Long reservationId, Date checkInDate, Date checkOutDate, int numberOfAdults,
			int numberOfChildren, double totalAmount, String reservationStatus) {
		super();
		this.reservationId = reservationId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
		this.totalAmount = totalAmount;
		this.reservationStatus = reservationStatus;
	}


	public Long getReservationId() {
		return reservationId;
	}


	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}


	public Date getCheckInDate() {
		return checkInDate;
	}


	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}


	public Date getCheckOutDate() {
		return checkOutDate;
	}


	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}


	public int getNumberOfAdults() {
		return numberOfAdults;
	}


	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}


	public int getNumberOfChildren() {
		return numberOfChildren;
	}


	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}


	public double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getReservationStatus() {
		return reservationStatus;
	}


	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	
	
	


}
