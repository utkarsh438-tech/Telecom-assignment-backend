package com.example.assignment.backend.Service.impl;

import com.example.assignment.backend.Dto.ShelfPositionDTO;
import com.example.assignment.backend.Service.ShelfPositionService;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class shelfpositionserviceimpl implements ShelfPositionService {
    private final Driver neo4jDriver;

public shelfpositionserviceimpl(Driver neo4jDriver) {
    this.neo4jDriver = neo4jDriver;
}
    @Override
    public List<ShelfPositionDTO> getShelfPositionByDeviceId(String deviceId) {
try(Session session = neo4jDriver.session()){
    var result=session.run(
            "MATCH(d:Device {id:$id,isDeleted:false})-[:HAS]->(sp:ShelfPosition) +" +
                    "WHERE sp.isDeleted=false RETURN sp",
            org.neo4j.driver.Values.parameters("id",deviceId)
    );
    List<ShelfPositionDTO> positions=new ArrayList<>();
    result.list().forEach(row->{
        var node=row.get("sp").asNode();
        ShelfPositionDTO dto=new ShelfPositionDTO();

        dto.setId(UUID.fromString(node.get("id").asString()));
        dto.setDeviceId(UUID.fromString(node.get("deviceId").asString()));
        dto.setAllocated(node.get("allocated").asBoolean());
        dto.setDeleted(node.get("isDeleted").asBoolean());
        positions.add(dto);



    });
    return positions;

    }
}

    @Override
    public ShelfPositionDTO getShelfPositionById(String Id) {
        try(Session session = neo4jDriver.session()){
            var result=session.run(
                    "MATCH(sp:ShelfPosition {id:$id,isDeleted:false}) return sp",
                    org.neo4j.driver.Values.parameters("id",Id)
            );
            if(result.hasNext()){
                var node=result.next().get("sp").asNode();
                ShelfPositionDTO dto=new ShelfPositionDTO();
                dto.setId(UUID.fromString(node.get("id").asString()));
                dto.setDeviceId(UUID.fromString(node.get("deviceId").asString()));
                dto.setAllocated(node.get("allocated").asBoolean());
                dto.setDeleted(node.get("isDeleted").asBoolean());
                return dto;
            }
            return null;
        }
    }

    @Override
    public ShelfPositionDTO freeShelfPosition(String Id) {
    try(Session session = neo4jDriver.session()){
        var result=session.run(
                "MATCH(sp:ShelfPosition {id:$id,isDeleted:false})"+
                        "SET sp.allocated=false return sp",
                org.neo4j.driver.Values.parameters("id",Id)
        );
        if(result.hasNext()){
            return getShelfPositionById(Id);
        }
    }

        return null;
    }


}



