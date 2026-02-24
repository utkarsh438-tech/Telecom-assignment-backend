package com.example.assignment.backend.Model;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;


public class ShelfPosition {


    private UUID id ;

    private UUID deviceId;
    private boolean allocated = false;
    private boolean isDeleted=false;
}
