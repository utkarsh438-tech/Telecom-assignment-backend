package com.example.assignment.backend.Controller;

import com.example.assignment.backend.Dto.ShelfDTO;
import com.example.assignment.backend.Service.ShelfService;
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

@WebMvcTest(ShelfController.class)
class ShelfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean

    private ShelfService shelfService;

    @Test
    void getAllShelves_returnsList() throws Exception {
        ShelfDTO dto = new ShelfDTO();
        dto.setId("s1");
        dto.setShelfName("Shelf A");

        when(shelfService.getAllShelves()).thenReturn(List.of(dto));

        mockMvc.perform(get("/shelves"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("s1"))
                .andExpect(jsonPath("$[0].shelfName").value("Shelf A"));
    }

    @Test
    void addShelf_returnsOk() throws Exception {
        when(shelfService.addShelfToPosition(any(), any())).thenReturn("new-shelf-id");

        String body = """
          {
            "name": "New Shelf",
            "partNumber": "SH-123"
          }
          """;

        mockMvc.perform(post("/shelves/create/pos-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string("Shelf created successfully with id: new-shelf-id"));
    }

    @Test
    void deleteShelf_returnsOk_whenDeleted() throws Exception {
        when(shelfService.deleteShelf("s1")).thenReturn(true);

        mockMvc.perform(delete("/shelves/s1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Shelf deleted successfully"));
    }
}

