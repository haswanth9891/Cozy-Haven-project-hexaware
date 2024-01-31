package com.hexaware.ccozyhaven.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Room_Details")
public class Room {

    @Id
    @Column(name = "room_id")
    private Long roomId;

    @NotBlank(message = "Room size is required")
    @Size(max = 20, message = "Room size must be at most 20 characters")
    @Column(name = "room_size")
    private String roomSize;

    @NotBlank(message = "Bed type is required")
    @Size(max = 20, message = "Bed type must be at most 20 characters")
    @Column(name = "bed_type")
    private String bedType;

    @Positive(message = "Max occupancy must be a positive number")
    @Column(name = "max_occupancy")
    private int maxOccupancy;

    @DecimalMin(value = "0.00", inclusive = false, message = "Base fare must be greater than 0.00")
    @Column(name = "base_fare")
    private BigDecimal baseFare;

    @Column(name = "is_ac")
    private boolean isAC;

    @NotBlank(message = "Availability status is required")
    @Size(max = 20, message = "Availability status must be at most 20 characters")
    @Column(name = "availability_status")
    private String availabilityStatus;

    public Room() {
        super();
    }

    public Room(Long roomId, String roomSize, String bedType, int maxOccupancy, BigDecimal baseFare, boolean isAC,
            String availabilityStatus) {
        super();
        this.roomId = roomId;
        this.roomSize = roomSize;
        this.bedType = bedType;
        this.maxOccupancy = maxOccupancy;
        this.baseFare = baseFare;
        this.isAC = isAC;
        this.availabilityStatus = availabilityStatus;
    }

    // Getter and Setter methods...

    @Override
    public String toString() {
        return "Room [roomId=" + roomId + ", roomSize=" + roomSize + ", bedType=" + bedType + ", maxOccupancy="
                + maxOccupancy + ", baseFare=" + baseFare + ", isAC=" + isAC + ", availabilityStatus="
                + availabilityStatus + "]";
    }
}