package com.example.assignment.backend.Controller;

import com.example.assignment.backend.Dto.DeviceDTO;
import com.example.assignment.backend.Model.Device;
import com.example.assignment.backend.Service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.MidiDeviceReceiver;
import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @PostMapping("")
    public ResponseEntity<?> CreateDevice(@RequestBody DeviceDTO devicedto) {
        try {
            String deviceId = deviceService.createDevice(devicedto);
            devicedto.setId(deviceId);
            return ResponseEntity.status(HttpStatus.CREATED).body(devicedto);

        } catch (Exception ex) {
            ex.printStackTrace(); // log the error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + ex.getMessage() + "\"}");
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> showDevice( @PathVariable String id) {
        DeviceDTO device = deviceService.getDeviceById(id);
        if (device != null) {
            return ResponseEntity.ok(device);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("")
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<DeviceDTO> devices = deviceService.getAllDevices();
        if (devices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(devices);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateDevice(@PathVariable String id, @RequestBody DeviceDTO devicedto) {
        DeviceDTO updated = deviceService.updateDevice(id, devicedto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable String id) {
        boolean deleted = deviceService.deleteDevice(id);
        return deleted ? ResponseEntity.ok("Device deleted successfully") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found");
    }
}
