package com.example.assignment.backend.Service;

import com.example.assignment.backend.Dto.ShelfDTO;
import com.example.assignment.backend.Service.impl.ShelfServiceImpl;
import org.junit.jupiter.api.Test;
//import org.neo4j.driver.*;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShelfServiceImplTest {

    @Test
    void getShelfById_returnsShelf_whenFound() {
        Driver driver = mock(Driver.class);
        Session session = mock(Session.class);
        Result result = mock(Result.class);
        Record record = mock(Record.class);
        Node node = mock(Node.class);

        when(driver.session()).thenReturn(session);
        when(session.run(anyString(), (TransactionConfig) any())).thenReturn(result);
        when(result.hasNext()).thenReturn(true);
        when(result.next()).thenReturn(record);
        when(record.get("s").asNode()).thenReturn(node);

        when(node.get("id").asString()).thenReturn("s1");
        when(node.get("name").asString()).thenReturn("Shelf A");
        when(node.get("partNumber").asString()).thenReturn("SH-1");
        when(node.get("isDeleted").asBoolean()).thenReturn(false);

        ShelfServiceImpl service = new ShelfServiceImpl(driver);

        ShelfDTO dto = service.getShelfById("s1");

        assertNotNull(dto);
        assertEquals("s1", dto.getId());
        assertEquals("Shelf A", dto.getShelfName());
    }

    @Test
    void getAllShelves_returnsList_whenShelvesExist() {
        Driver driver = mock(Driver.class);
        Session session = mock(Session.class);
        Result result = mock(Result.class);
        Record record = mock(Record.class);
        Node node = mock(Node.class);

        when(driver.session()).thenReturn(session);
        when(session.run(anyString())).thenReturn(result);
        when(result.list()).thenReturn(List.of(record));
        when(record.get("s").asNode()).thenReturn(node);

        when(node.get("id").asString()).thenReturn("s1");
        when(node.get("name").asString()).thenReturn("Shelf A");
        when(node.get("partNumber").asString()).thenReturn("SH-1");
        when(node.get("isDeleted").asBoolean()).thenReturn(false);

        ShelfServiceImpl service = new ShelfServiceImpl(driver);

        List<ShelfDTO> shelves = service.getAllShelves();

        assertEquals(1, shelves.size());
        assertEquals("s1", shelves.get(0).getId());
    }
}
