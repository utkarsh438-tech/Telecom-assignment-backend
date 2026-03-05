package com.example.assignment.backend.Service;
import com.example.assignment.backend.Dto.ShelfPositionDTO;
import com.example.assignment.backend.Service.impl.shelfpositionserviceimpl;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.*;
import org.neo4j.driver.types.Node;

import java.util.List;
import java.util.UUID;
import org.neo4j.driver.Record;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class shelfpositionserviceimplTest {

    @Test
    void getShelfPositionByDeviceId_returnsPositions() {
        Driver driver = mock(Driver.class);
        Session session = mock(Session.class);
        Result result = mock(Result.class);
        Record record = mock(Record.class);
        Node spNode = mock(Node.class);

        when(driver.session()).thenReturn(session);
        when(session.run(anyString(), (TransactionConfig) any())).thenReturn(result);
        when(result.list()).thenReturn(List.of(record));
        when(record.get("sp").asNode()).thenReturn(spNode);

        when(spNode.get("id").asString()).thenReturn(UUID.randomUUID().toString());
        when(spNode.get("deviceId").asString()).thenReturn(UUID.randomUUID().toString());
        when(spNode.get("allocated").asBoolean()).thenReturn(false);
        when(spNode.get("isDeleted").asBoolean()).thenReturn(false);
        when(spNode.get("positionNumber").asInt()).thenReturn(1);

        shelfpositionserviceimpl service = new shelfpositionserviceimpl(driver);

        List<ShelfPositionDTO> positions = service.getShelfPositionByDeviceId("dev-1");

        assertEquals(1, positions.size());
        assertEquals(1, positions.get(0).getPositionNumber());
    }

    @Test
    void freeShelfPosition_returnsUpdated_whenMatchExists() {
        Driver driver = mock(Driver.class);
        Session session = mock(Session.class);
        Result result = mock(Result.class);

        when(driver.session()).thenReturn(session);
        when(session.run(startsWith("MATCH(sp:ShelfPosition"), (TransactionConfig) any())).thenReturn(result);
        when(result.hasNext()).thenReturn(true);

        // stub getShelfPositionById path
        Result result2 = mock(Result.class);
        Record record = mock(Record.class);
        Node node = mock(Node.class);
        when(session.run(startsWith("MATCH(sp:ShelfPosition {id:$id"), (TransactionConfig) any())).thenReturn(result2);
        when(result2.hasNext()).thenReturn(true);
        when(result2.next()).thenReturn(record);
        when(record.get("sp").asNode()).thenReturn(node);
        when(node.get("id").asString()).thenReturn(UUID.randomUUID().toString());
        when(node.get("deviceId").asString()).thenReturn(UUID.randomUUID().toString());
        when(node.get("allocated").asBoolean()).thenReturn(false);
        when(node.get("isDeleted").asBoolean()).thenReturn(false);

        shelfpositionserviceimpl service = new shelfpositionserviceimpl(driver);

        ShelfPositionDTO dto = service.freeShelfPosition("pos-1");

        assertNotNull(dto);
        assertFalse(dto.isAllocated());
    }
}
