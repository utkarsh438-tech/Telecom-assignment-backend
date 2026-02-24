package com.example.assignment.backend.Service;


import com.example.assignment.backend.Dto.ShelfPositionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShelfPositionService {
    List<ShelfPositionDTO>getShelfPositionByDeviceId(String deviceId);
    ShelfPositionDTO getShelfPositionById(String Id);
    ShelfPositionDTO freeShelfPosition(String Id);
}
