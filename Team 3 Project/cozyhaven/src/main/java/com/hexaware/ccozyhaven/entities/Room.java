package com.hexaware.ccozyhaven.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Room_Details")
public class Room {

	@Id
	@Column(name = "room_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Pattern(regexp = "^[0-9]+$")
	private Long roomId;

	@NotBlank(message = "Room size is required")
	@Size(max = 20, message = "Room size must be at most 20 characters")
	@Column(name = "room_size")
	private String roomSize;

	@NotBlank(message = "Bed type is required")
	@Size(max = 20, message = "Bed type must be at most 20 characters")
	@Pattern(regexp = "single bed|double bed|king size", message = "Invalid bed type")
	@Column(name = "bed_type")
	private String bedType;

	@Positive(message = "Max occupancy must be a positive number")
	@Column(name = "max_occupancy")
	private int maxOccupancy;

	@DecimalMin(value = "0.00", inclusive = false, message = "Base fare must be greater than 0.00")
	@Column(name = "base_fare")
	private double baseFare;

	@Column(name = "is_ac")
	private boolean isAC;

	@NotBlank(message = "Availability status is required")
	@Size(max = 20, message = "Availability status must be at most 20 characters")
	@Column(name = "availability_status")
	private boolean availabilityStatus;

	@ManyToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	public Room() {
		super();
	}

	public Room(@Pattern(regexp = "^[0-9]+$") Long roomId,
			@NotBlank(message = "Room size is required") @Size(max = 20, message = "Room size must be at most 20 characters") String roomSize,
			@NotBlank(message = "Bed type is required") @Size(max = 20, message = "Bed type must be at most 20 characters") @Pattern(regexp = "single bed|double bed|king size", message = "Invalid bed type") String bedType,
			@Positive(message = "Max occupancy must be a positive number") int maxOccupancy,
			@DecimalMin(value = "0.00", inclusive = false, message = "Base fare must be greater than 0.00") double baseFare,
			boolean isAC,
			@NotBlank(message = "Availability status is required") @Size(max = 20, message = "Availability status must be at most 20 characters") boolean availabilityStatus,
			Reservation reservation, Hotel hotel) {
		super();
		this.roomId = roomId;
		this.roomSize = roomSize;
		this.bedType = bedType;
		this.maxOccupancy = maxOccupancy;
		this.baseFare = baseFare;
		this.isAC = isAC;
		this.availabilityStatus = availabilityStatus;
		this.reservation = reservation;
		this.hotel = hotel;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(String roomSize) {
		this.roomSize = roomSize;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public int getMaxOccupancy() {
		return maxOccupancy;
	}

	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	public double getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(double baseFare) {
		this.baseFare = baseFare;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public boolean isAC() {
		return isAC;
	}

	public void setAC(boolean isAC) {
		this.isAC = isAC;
	}

	public boolean isAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(boolean availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", roomSize=" + roomSize + ", bedType=" + bedType + ", maxOccupancy="
				+ maxOccupancy + ", baseFare=" + baseFare + ", isAC=" + isAC + ", availabilityStatus="
				+ availabilityStatus + ", reservation=" + reservation + ", hotel=" + hotel + "]";
	}

}
