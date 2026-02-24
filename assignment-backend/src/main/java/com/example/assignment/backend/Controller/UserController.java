package com.example.assignment.backend.Controller;


public class UserController {

//    private final Driver neo4jDriver;
//
//    public DeviceService(Driver neo4jDriver) {
//        this.neo4jDriver = neo4jDriver;
//    }
//
//    // Create device with n shelf positions
//    public String createDevice(DeviceDTO dto) {
//        try (Session session = neo4jDriver.session()) {
//            String deviceId = UUID.randomUUID().toString();
//
//            session.run(
//                    "CREATE (d:Device {id: $id, deviceName: $deviceName, partNumber: $partNumber, " +
//                            "buildingName: $buildingName, deviceType: $deviceType, numShelfPositions: $numShelfPositions, isDeleted: false})",
//                    org.neo4j.driver.Values.parameters(
//                            "id", deviceId,
//                            "deviceName", dto.getDeviceName(),
//                            "partNumber", dto.getPartNumber(),
//                            "buildingName", dto.getBuildingName(),
//                            "deviceType", dto.getDeviceType(),
//                            "numShelfPositions", dto.getNumShelfPositions()
//                    )
//            );
//
//            // Auto-create shelf positions
//            for (int i = 1; i <= dto.getNumShelfPositions(); i++) {
//                session.run(
//                        "MATCH (d:Device {id: $id}) " +
//                                "CREATE (sp:ShelfPosition {id: randomUUID(), positionNumber: $pos, allocated: false, isDeleted: false, deviceId: $id}) " +
//                                "CREATE (d)-[:HAS]->(sp)",
//                        org.neo4j.driver.Values.parameters("id", deviceId, "pos", i)
//                );
//            }
//
//            return deviceId;
//        }
//    }
//
//    // Get all devices
//    public List<Record> getAllDevices() {
//        try (Session session = neo4jDriver.session()) {
//            Result result = session.run("MATCH (d:Device) WHERE d.isDeleted = false RETURN d");
//            return result.list();
//        }
//    }
//
//    // Get device by ID
//    public Record getDeviceById(UUID id) {
//        try (Session session = neo4jDriver.session()) {
//            Result result = session.run(
//                    "MATCH (d:Device {id: $id, isDeleted: false})-[:HAS]->(sp:ShelfPosition) " +
//                            "OPTIONAL MATCH (sp)-[:HAS]->(s:Shelf) " +
//                            "RETURN d, collect(sp) AS shelfPositions, collect(s) AS shelves",
//                    org.neo4j.driver.Values.parameters("id", id.toString())
//            );
//            return result.hasNext() ? result.next() : null;
//        }
//    }
//
//    // Update device properties
//    public boolean updateDevice(UUID id, DeviceDTO dto) {
//        try (Session session = neo4jDriver.session()) {
//            Result result = session.run(
//                    "MATCH (d:Device {id: $id, isDeleted: false}) " +
//                            "SET d.deviceName = $deviceName, d.partNumber = $partNumber, " +
//                            "d.buildingName = $buildingName, d.deviceType = $deviceType RETURN d",
//                    org.neo4j.driver.Values.parameters(
//                            "id", id.toString(),
//                            "deviceName", dto.getDeviceName(),
//                            "partNumber", dto.getPartNumber(),
//                            "buildingName", dto.getBuildingName(),
//                            "deviceType", dto.getDeviceType()
//                    )
//            );
//            return result.hasNext();
//        }
//    }
//
//    // Soft delete device + cascade shelf positions
//    public boolean deleteDevice(UUID id) {
//        try (Session session = neo4jDriver.session()) {
//            Result result = session.run(
//                    "MATCH (d:Device {id: $id})-[:HAS]->(sp:ShelfPosition) " +
//                            "OPTIONAL MATCH (sp)-[:HAS]->(s:Shelf) " +
//                            "SET d.isDeleted = true, sp.isDeleted = true, sp.allocated = false, s.isDeleted = true RETURN d",
//                    org.neo4j.driver.Values.parameters("id", id.toString())
//            );
//            return result.hasNext();
//        }
//    }
}
