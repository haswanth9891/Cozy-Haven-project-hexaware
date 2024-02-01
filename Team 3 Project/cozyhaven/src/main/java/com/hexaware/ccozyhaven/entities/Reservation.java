package com.hexaware.ccozyhaven.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Reservation_Details")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Pattern(regexp = "^[0-9]+$")
    @Column(name = "reservation_id")
    private Long reservationId;

    @NotNull(message = "Check-in date cannot be null")
    @Column(name = "check_in_date")
    private Date checkInDate;

    @NotNull(message = "Check-out date cannot be null")
    @Future(message = "Check-out date must be in the future")
    @Column(name = "check_out_date")
    private Date checkOutDate;

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
   

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id") // This should be the foreign key column in the Room table
    private Set<Room> rooms = new HashSet<>();


    public Reservation() {
        super();
    }

    public Reservation(Long reservationId, Date checkInDate, Date checkOutDate, int numberOfAdults,
                       int numberOfChildren, double totalAmount, String reservationStatus, Set<Room> rooms) {
        super();
        this.reservationId = reservationId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.totalAmount = totalAmount;
        this.reservationStatus = reservationStatus;
        this.rooms = rooms;
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

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
    public String toString() {
        return "Reservation [reservationId=" + reservationId + ", checkInDate=" + checkInDate + ", checkOutDate="
                + checkOutDate + ", numberOfAdults=" + numberOfAdults + ", numberOfChildren=" + numberOfChildren
                + ", totalAmount=" + totalAmount + ", reservationStatus=" + reservationStatus + ", rooms=" + rooms + "]";
    }
}
