package com.example.assignment.backend.Model;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Node
public class Device {


    @Id
    @GeneratedValue
    private UUID id;

    private String deviceName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPartNumber() {
        return PartNumber;
    }

    public void setPartNumber(String partNumber) {
        PartNumber = partNumber;
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

    public int getNumShelfPosition() {
        return numShelfPosition;
    }

    public void setNumShelfPosition(int numShelfPosition) {
        this.numShelfPosition = numShelfPosition;
    }

    public boolean isDelete() {
        return isDeleted;
    }

    public void setDelete(boolean delete) {
        isDeleted = delete;
    }

    private String PartNumber;
    private String buildingName;
    private String deviceType;


    
    private int numShelfPosition;
    private boolean isDeleted=false;

    @Relationship(type = "HAS" ,direction = Relationship.Direction.OUTGOING)
    private List<ShelfPosition> shelfPositions=new ArrayList<>();




}
