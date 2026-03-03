package com.example.assignment.backend.Model;


import jakarta.validation.constraints.NotBlank;
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
    private String id;

    @NotBlank
    private String deviceName;



    @NotBlank
    private String partNumber;
    @NotBlank
    private String buildingName;
    @NotBlank
    private String deviceType;


    @NotBlank
    private int numShelfPositions;

    private boolean isDelete=false;

    @Relationship(type = "HAS" ,direction = Relationship.Direction.OUTGOING)
    private List<ShelfPosition> shelfPositions=new ArrayList<>();




}
