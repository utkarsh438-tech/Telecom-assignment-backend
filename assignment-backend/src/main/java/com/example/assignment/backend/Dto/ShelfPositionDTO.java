package com.example.assignment.backend.Dto;

import java.util.UUID;

public class ShelfPositionDTO {
    private UUID id ;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }

    //    private int positionNumber;
    private UUID deviceId;
    private int positionNumber;
    private boolean allocated = false;

    public int getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }

    private boolean isDeleted=false;
}
