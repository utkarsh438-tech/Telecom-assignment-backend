package com.example.assignment.backend.Dto;


import java.util.UUID;

public class DeviceDTO {
    private String id;
    private String deviceName;
    private String partNumber;      // fixed capitalization
    private String buildingName;
    private String deviceType;
    private int numShelfPositions;  // plural for consistency

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPartNumber() {
        return partNumber;
    }
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getBuildingName() {
        return buildingName;
    }
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getDeviceType() {
        return deviceType;
    }
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getNumShelfPositions() {
        return numShelfPositions;
    }
    public void setNumShelfPositions(int numShelfPositions) {
        this.numShelfPositions = numShelfPositions;
    }
    private boolean isDeleted=false;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean delete) {
        this.isDeleted = delete;
    }
}

