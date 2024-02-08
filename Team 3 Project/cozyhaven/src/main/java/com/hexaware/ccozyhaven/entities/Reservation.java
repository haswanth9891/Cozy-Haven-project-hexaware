package com.hexaware.ccozyhaven.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Reservation_Details")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "reservation_id")
	private Long reservationId;

	@NotNull(message = "Check-in date cannot be null")
	@Column(name = "check_in_date")
	private LocalDate checkInDate;

	@NotNull(message = "Check-out date cannot be null")
	@Future(message = "Check-out date must be in the future")
	@Column(name = "check_out_date")
	private LocalDate checkOutDate;

	@Column(name = "num_adults")
	@NotNull(message = "Number of adults cannot be null")
	private int numberOfAdults;

	@Column(name = "num_children")
	@NotNull(message = "Number of children cannot be null")
	private int numberOfChildren;

	@Column(name = "total_amount")
	private double totalAmount;

	@NotNull(message = "Reservation status cannot be null")
	@Column(name = "reservation_status")
	private String reservationStatus;

	@Column(name = "refund_processed")
	private boolean refundProcessed;

	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
	private Set<Room> rooms = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	public Reservation() {
		super();
	}

	public Reservation(Long reservationId, @NotNull(message = "Check-in date cannot be null") LocalDate checkInDate,
			@NotNull(message = "Check-out date cannot be null") @Future(message = "Check-out date must be in the future") LocalDate checkOutDate,
			@NotNull(message = "Number of adults cannot be null") int numberOfAdults,
			@NotNull(message = "Number of children cannot be null") int numberOfChildren, double totalAmount,
			@NotNull(message = "Reservation status cannot be null") String reservationStatus) {
		super();
		this.reservationId = reservationId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
		this.totalAmount = totalAmount;
		this.reservationStatus = reservationStatus;
	}

	public Reservation(@NotNull(message = "Check-in date cannot be null") LocalDate checkInDate,
			@NotNull(message = "Check-out date cannot be null") @Future(message = "Check-out date must be in the future") LocalDate checkOutDate,
			@NotNull(message = "Number of adults cannot be null") int numberOfAdults,
			@NotNull(message = "Number of children cannot be null") int numberOfChildren, double totalAmount,
			@NotNull(message = "Reservation status cannot be null") String reservationStatus) {
		super();
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

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
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

	public boolean isRefundProcessed() {
		return refundProcessed;
	}

	public void setRefundProcessed(boolean refundProcessed) {
		this.refundProcessed = refundProcessed;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", checkInDate=" + checkInDate + ", checkOutDate="
				+ checkOutDate + ", numberOfAdults=" + numberOfAdults + ", numberOfChildren=" + numberOfChildren
				+ ", totalAmount=" + totalAmount + ", reservationStatus=" + reservationStatus + ", refundProcessed="
				+ refundProcessed + ", rooms=" + rooms + ", user=" + user + ", hotel=" + hotel + "]";
	}

}
