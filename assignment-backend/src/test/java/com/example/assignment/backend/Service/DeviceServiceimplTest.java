package com.example.assignment.backend.Service;
import com.example.assignment.backend.Dto.DeviceDTO;
import com.example.assignment.backend.Service.impl.DeviceServiceimpl;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.*;
import org.neo4j.driver.types.Node;

import org.neo4j.driver.Record;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class DeviceServiceimplTest {
    @Test
    void getDeviceById_returnsDevice_whenFound() {
        Driver driver = mock(Driver.class);
        Session session = mock(Session.class);
        Result result = mock(Result.class);
        Record record = mock(Record.class);
        Node node = mock(Node.class);

        when(driver.session()).thenReturn(session);
        when(session.run(anyString(), (TransactionConfig) any())).thenReturn(result);
        when(result.hasNext()).thenReturn(true);
        when(result.next()).thenReturn(record);
        when(record.get("d").asNode()).thenReturn(node);

        when(node.get("id").asString()).thenReturn("id-1");
        when(node.get("deviceName").asString()).thenReturn("Device 1");
        when(node.get("partNumber").asString()).thenReturn("PN-1");
        when(node.get("buildingName").asString()).thenReturn("Building");
        when(node.get("deviceType").asString()).thenReturn("Type");
        when(node.get("numShelfPositions").asInt()).thenReturn(4);

        DeviceServiceimpl service = new DeviceServiceimpl(null, driver);

        DeviceDTO dto = service.getDeviceById("id-1");

        assertNotNull(dto);
        assertEquals("id-1", dto.getId());
        assertEquals("Device 1", dto.getDeviceName());
        assertEquals(4, dto.getNumShelfPositions());
        verify(session).run(anyString(), (TransactionConfig) any());
        verify(session).close();
    }

    @Test
    void getAllDevices_returnsEmptyList_whenNoDevices() {
        Driver driver = mock(Driver.class);
        Session session = mock(Session.class);
        Result result = mock(Result.class);

        when(driver.session()).thenReturn(session);
        when(session.run(anyString())).thenReturn(result);
        when(result.list()).thenReturn(List.of());

        DeviceServiceimpl service = new DeviceServiceimpl(null, driver);

        List<DeviceDTO> list = service.getAllDevices();

        assertNotNull(list);
        assertTrue(list.isEmpty());
        verify(session).run(anyString());
    }

    @Test
    void deleteDevice_returnsTrue_whenCypherMatchesRows() {
        Driver driver = mock(Driver.class);
        Session session = mock(Session.class);
        Result result = mock(Result.class);

        when(driver.session()).thenReturn(session);
        when(session.run(anyString(), (TransactionConfig) any())).thenReturn(result);
        when(result.hasNext()).thenReturn(true);

        DeviceServiceimpl service = new DeviceServiceimpl(null, driver);

        boolean deleted = service.deleteDevice("id-1");

        assertTrue(deleted);
    }

    @Test
    void deleteDevice_returnsFalse_whenNoMatch() {
        Driver driver = mock(Driver.class);
        Session session = mock(Session.class);
        Result result = mock(Result.class);

        when(driver.session()).thenReturn(session);
        when(session.run(anyString(), (TransactionConfig) any())).thenReturn(result);
        when(result.hasNext()).thenReturn(false);

        DeviceServiceimpl service = new DeviceServiceimpl(null, driver);

        boolean deleted = service.deleteDevice("id-1");

        assertFalse(deleted);
    }
}
