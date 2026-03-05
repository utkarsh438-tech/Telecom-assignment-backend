package com.example.assignment.backend.Service.impl;

import com.example.assignment.backend.Dto.ShelfDTO;
import com.example.assignment.backend.Exception.BusinessException;
import com.example.assignment.backend.Exception.ResourceNotFoundException;
import com.example.assignment.backend.Service.ShelfService;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class ShelfServiceImpl implements ShelfService {
    private final Driver neo4jDriver;

    public ShelfServiceImpl(Driver neo4jDriver) {
        this.neo4jDriver = neo4jDriver;
    }

    @Override
    public String addShelfToPosition(String shelfpositionId, ShelfDTO shelfDTO) {
        try (Session session = neo4jDriver.session()) {
            String shelfId = UUID.randomUUID().toString();
            session.run("MATCH (sp:ShelfPosition {id: $shelfpositionId, allocated: false, isDeleted: false}) "
                    + "CREATE (s:Shelf {id: $id, name: $name, partNumber: $partNumber, isDeleted: false}) "
                    + "CREATE (sp)-[:HAS]->(s) " +
                            "SET sp.allocated = true",
                    org.neo4j.driver.Values.parameters("shelfpositionId",shelfpositionId,
                            "id", shelfId,
                            "name", shelfDTO.getShelfName(),
                            "partNumber", shelfDTO.getPartNumber()));
            return shelfId;
        }catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Failed to create shelf", e);
        }
    }

    @Override
    public ShelfDTO getShelfById(String id) {
        try (Session session = neo4jDriver.session()) {
            var result = session.run(
                    "MATCH  (d:Device)-[:HAS]->(sp:ShelfPosition)-[:HAS]->(s:Shelf {id:$id,isDeleted:false}) "+
                            "WHERE sp.isDeleted = false  AND d.isDeleted=false " +
                                    " RETURN s,sp,d",
                    org.neo4j.driver.Values.parameters("id", id));
            if (result.hasNext()) {
                var record = result.next();
                var node = record.get("s").asNode();
                var dnode=record.get("d").asNode();
                var spnode=record.get("sp").asNode();


                ShelfDTO dto = new ShelfDTO();
                dto.setId((node.get("id").asString()));
                dto.setShelfName(node.get("name").asString());
                dto.setPartNumber(node.get("partNumber").asString());
                dto.setDeleted(node.get("isDeleted").asBoolean());
                dto.setDeviceName(node.get("deviceName").asString());

                dto.setDeviceName(dnode.get("deviceName").asString());
                dto.setDeviceId(dnode.get("deviceId").asString());
                dto.setPosition(spnode.get("positionNumber").asInt());

                return dto;
            }
            return null;
        }catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Failed to fetch Shelf: " + id, e);
        }
    }

    @Override
    public List<ShelfDTO> getAllShelves() {
        try (Session session = neo4jDriver.session()) {
            var result = session.run("MATCH  (d:Device)-[:HAS]->(sp:ShelfPosition)-[:HAS]->(s:Shelf {isDeleted:false}) "+
                            "WHERE sp.isDeleted = false  AND d.isDeleted=false " +
                            " RETURN s,sp,d");
            List<ShelfDTO> shelves = new ArrayList<>();
            result.list().forEach(record -> {
                var node = record.get("s").asNode();
                var  dnode = record.get("d").asNode();
                var spnode = record.get("sp").asNode();

                ShelfDTO dto = new ShelfDTO();
                dto.setId((node.get("id").asString()));
                dto.setShelfName(node.get("name").asString());
                dto.setPartNumber(node.get("partNumber").asString());
                dto.setDeleted(node.get("isDeleted").asBoolean());

                dto.setDeviceName(dnode.get("deviceName").asString());
                dto.setPosition(spnode.get("positionNumber").asInt());
                dto.setDeviceId(dnode.get("deviceId").asString());
                shelves.add(dto);
            });
            return shelves;
        }catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Failed to fetch shelves" , e);
        }
    }

    @Override
    public ShelfDTO updateShelf(String id, ShelfDTO shelfDTO) {
        try (Session session = neo4jDriver.session()) {
            var result = session.run("MATCH (s:Shelf {id:$id, isDeleted:false}) "
                    + "SET s.name = $name, s.partNumber = $partNumber RETURN s",
                    org.neo4j.driver.Values.parameters("id", id, "name", shelfDTO.getShelfName(), "partNumber", shelfDTO.getPartNumber()));
            if (result.hasNext()) {
                return getShelfById(id);
            }
            return null;
        }catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Failed to update Shelf: " + id, e);
        }
    }

    @Override
    public boolean deleteShelf(String id) {
        try (Session session = neo4jDriver.session()) {
            var result = session.run("MATCH (s:Shelf {id:$id}) "
                    + "SET s.isDeleted = true " + "WITH s "
                    + "OPTIONAL MATCH (sp:ShelfPosition)-[r:HAS]->(s) "
                    + "SET sp.allocated = false " + "DELETE r RETURN s",
                    org.neo4j.driver.Values.parameters("id", id));
            return result.hasNext();
        }catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Failed to delete device: " + id, e);
        }
    }

}
