package com.example.assignment.backend.Service.impl;

import com.example.assignment.backend.Dto.DeviceDTO;
import com.example.assignment.backend.Service.DeviceService;
import org.modelmapper.ModelMapper;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceServiceimpl implements DeviceService {
    private final ModelMapper modelMapper;
    private final Driver neo4jDriver;


    public DeviceServiceimpl(ModelMapper modelMapper, Driver neo4jDriver) {
        this.modelMapper = modelMapper;
        this.neo4jDriver = neo4jDriver;
    }


    @Override
    public String createDevice(DeviceDTO devicedto) {
        try
                (Session session = neo4jDriver.session()) {
            String deviceId = UUID.randomUUID().toString();
            session.run(
                    "CREATE (d:Device {id: $id," +
                            " deviceName: $deviceName, " +
                            "partNumber: $partNumber, " +
                            "buildingName: $buildingName, " +
                            "deviceType: $deviceType, " +
                            "numShelfPositions: $numShelfPositions," +                  // TO DO REMOVE NO. OF SP EDITABLE AND ADD ERROR MSG TO SHOW INVALID RATHER 500 AUTO GENEREATE ERROR FROM SERVER
                            " isDeleted: false})",
                    org.neo4j.driver.Values.parameters(
                            "id", deviceId,
                            "deviceName", devicedto.getDeviceName(),
                            "partNumber", devicedto.getPartNumber(),
                            "buildingName", devicedto.getBuildingName(),
                            "deviceType", devicedto.getDeviceType(),
                            "numShelfPositions", devicedto.getNumShelfPositions()
                    )
            );
            for (int i = 1; i <= devicedto.getNumShelfPositions(); i++) {
                session.run(
                        "MATCH (d:Device {id: $id}) " +
                                "CREATE (sp:ShelfPosition {id: randomUUID(), " +
                                "positionNumber: $pos," +
                                " allocated: false," +
                                " isDeleted: false," +
                                " deviceId: $id}) " +
                                "CREATE (d)-[:HAS]->(sp)",
                        org.neo4j.driver.Values.parameters("id", deviceId, "pos", i)
                );
            }

            return deviceId;

        }
    }

    @Override
    public DeviceDTO getDeviceById(String id) {
        try (Session session = neo4jDriver.session()) {
            var result = session.run(
                    "MATCH(d:Device {id:$id,isDeleted:false}) return d",
                    org.neo4j.driver.Values.parameters("id", id)
            );
            if (result.hasNext()) {
                var node = result.next().get("d").asNode();
                DeviceDTO dto = new DeviceDTO();
                dto.setId(node.get("id").asString());
                dto.setDeviceName(node.get("deviceName").asString());
                dto.setPartNumber(node.get("partNumber").asString());
                dto.setBuildingName(node.get("buildingName").asString());
                dto.setDeviceType(node.get("deviceType").asString());
                dto.setNumShelfPositions(node.get("numShelfPositions").asInt());
                return dto;
            }
            return null;
        }
    }

    @Override
    public List<DeviceDTO> getAllDevices() {
        try (Session session = neo4jDriver.session()) {
            var result = session.run("MATCH (d:Device) WHERE d.isDeleted=false RETURN d");
            List<DeviceDTO> devices = new ArrayList<>();
            result.list().forEach(record -> {
                var node = record.get("d").asNode();
                DeviceDTO dto = new DeviceDTO();
                dto.setId(node.get("id").asString());
                dto.setDeviceName(node.get("deviceName").asString());
                dto.setPartNumber(node.get("partNumber").asString());
                dto.setBuildingName(node.get("buildingName").asString());
                dto.setDeviceType(node.get("deviceType").asString());
                dto.setNumShelfPositions(node.get("numShelfPositions").asInt());
                devices.add(dto);
            });
            return devices;
        }
    }

    @Override
    public DeviceDTO updateDevice(String id, DeviceDTO devicedto) {

        try (Session session = neo4jDriver.session()) {
            var result = session.run("MATCH (d:Device {id: $id, isDeleted: false}) "
                            + "SET d.deviceName = $deviceName, d.partNumber = $partNumber, "
                            + "d.buildingName = $buildingName, d.deviceType = $deviceType, "
                            + "d.numShelfPositions = $numShelfPositions RETURN d",
                    org.neo4j.driver.Values.parameters("id", id,
                            "deviceName", devicedto.getDeviceName(),
                            "partNumber", devicedto.getPartNumber(),
                            "buildingName", devicedto.getBuildingName(),
                            "deviceType", devicedto.getDeviceType(),
                            "numShelfPositions", devicedto.getNumShelfPositions()));
            if (result.hasNext()) {
                return getDeviceById(id);
            }
            return null;
        }
    }

    @Override
    public boolean deleteDevice(String id) {
        try (Session session = neo4jDriver.session()) {
            var result = session.run("MATCH (d:Device {id: $id})-[:HAS]->(sp:ShelfPosition) "
                    + "OPTIONAL MATCH (sp)-[:HAS]->(s:Shelf) " +
                    "SET d.isDeleted = true, sp.isDeleted = true, sp.allocated = false, "
                    + "s.isDeleted = true RETURN d",
                    org.neo4j.driver.Values.parameters("id",
                            id));
            return result.hasNext(); }
        }
    }


