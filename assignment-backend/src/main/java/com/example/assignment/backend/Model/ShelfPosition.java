package com.example.assignment.backend.Model;


import jakarta.validation.constraints.NotBlank;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;


public class ShelfPosition {

    @NotBlank
    private String id ;
    @NotBlank
    private String deviceId;
    private boolean allocated = false;
    private boolean isDeleted=false;
}
