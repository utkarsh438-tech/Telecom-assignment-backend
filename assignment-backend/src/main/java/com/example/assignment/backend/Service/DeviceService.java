package com.example.assignment.backend.Service;

import com.example.assignment.backend.Dto.DeviceDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceService {
    String createDevice(DeviceDTO devicedto);

    DeviceDTO getDeviceById(String id);

    List<DeviceDTO> getAllDevices();
    DeviceDTO updateDevice( String Id,DeviceDTO devicedto);
    boolean deleteDevice(String id);
}
