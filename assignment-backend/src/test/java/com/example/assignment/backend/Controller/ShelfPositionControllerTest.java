package com.example.assignment.backend.Controller;
import com.example.assignment.backend.Dto.ShelfPositionDTO;
import com.example.assignment.backend.Service.ShelfPositionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShelfPositionController.class)
class ShelfPositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShelfPositionService shelfPositionService;

    @Test
    void getAllShelfPositions_returnsList() throws Exception {
        ShelfPositionDTO dto = new ShelfPositionDTO();
        dto.setId(UUID.randomUUID());
        dto.setAllocated(false);

        when(shelfPositionService.getShelfPositionByDeviceId("dev-1")).thenReturn(List.of(dto));

        mockMvc.perform(get("/shelf-Position/device/dev-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].allocated").value(false));
    }

    @Test
    void freeShelfPosition_returns404_whenServiceReturnsNull() throws Exception {
        when(shelfPositionService.freeShelfPosition("pos-1")).thenReturn(null);

        mockMvc.perform(put("/shelf-Position/pos-1/delete"))
                .andExpect(status().isNotFound());
    }
}