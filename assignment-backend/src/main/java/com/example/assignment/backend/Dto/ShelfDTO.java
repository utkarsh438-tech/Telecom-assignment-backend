package com.example.assignment.backend.Dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ShelfDTO {


    private String id;
@JsonAlias("name")
    private String shelfName;
    private String partNumber;

    private boolean isDeleted=false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
@JsonAlias("name")
    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }


    private Integer position;
    public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }
    private String deviceName;
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
    }
    private  String deviceId;
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
