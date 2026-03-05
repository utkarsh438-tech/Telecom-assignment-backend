package com.example.assignment.backend.Controller;

import com.example.assignment.backend.Dto.DeviceDTO;
import com.example.assignment.backend.Service.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeviceService deviceService;

    @Test
    void getAllDevices_returns200WithList() throws Exception {
        DeviceDTO dto = new DeviceDTO();
        dto.setId("id-1");
        dto.setDeviceName("Device 1");

        when(deviceService.getAllDevices()).thenReturn(List.of(dto));

        mockMvc.perform(get("/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("id-1"))
                .andExpect(jsonPath("$[0].deviceName").value("Device 1"));
    }

    @Test
    void getDeviceById_returns404_whenNull() throws Exception {
        when(deviceService.getDeviceById("id-1")).thenReturn(null);

        mockMvc.perform(get("/devices/id-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createDevice_returns201() throws Exception {
        DeviceDTO dto = new DeviceDTO();
        dto.setId("new-id");
        dto.setDeviceName("New Device");
        when(deviceService.createDevice(any(DeviceDTO.class))).thenReturn("new-id");

        String body = """
          {
            "deviceName": "New Device",
            "partNumber": "PN-123",
            "buildingName": "Building",
            "deviceType": "Type",
            "numShelfPositions": 4
          }
          """;

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("new-id"))
                .andExpect(jsonPath("$.deviceName").value("New Device"));
    }

    @Test
    void deleteDevice_returns200_whenServiceTrue() throws Exception {
        when(deviceService.deleteDevice("id-1")).thenReturn(true);

        mockMvc.perform(delete("/devices/id-1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Device deleted successfully"));
    }
}

