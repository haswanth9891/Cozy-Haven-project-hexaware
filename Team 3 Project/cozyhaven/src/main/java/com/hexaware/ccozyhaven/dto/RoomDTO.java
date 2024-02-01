package com.hexaware.ccozyhaven.dto;

import java.math.BigDecimal;


public class RoomDTO {
	
	
    private Long roomId;
    private String roomSize;
    private String bedType;
    private int maxOccupancy;
    private BigDecimal baseFare;
    private boolean isAC;
    private String availabilityStatus;


	public RoomDTO() {
		super();
	}


	public RoomDTO(Long roomId, String roomSize, String bedType, int maxOccupancy, BigDecimal baseFare, boolean isAC,
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


	public BigDecimal getBaseFare() {
		return baseFare;
	}


	public void setBaseFare(BigDecimal baseFare) {
		this.baseFare = baseFare;
	}


	public boolean isAC() {
		return isAC;
	}


	public void setAC(boolean isAC) {
		this.isAC = isAC;
	}


	public String getAvailabilityStatus() {
		return availabilityStatus;
	}


	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}


	@Override
	public String toString() {
		return "RoomDTO [roomId=" + roomId + ", roomSize=" + roomSize + ", bedType=" + bedType + ", maxOccupancy="
				+ maxOccupancy + ", baseFare=" + baseFare + ", isAC=" + isAC + ", availabilityStatus="
				+ availabilityStatus + "]";
	}

    
}
