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
    private boolean allocated = false;
    private boolean isDeleted=false;
}
