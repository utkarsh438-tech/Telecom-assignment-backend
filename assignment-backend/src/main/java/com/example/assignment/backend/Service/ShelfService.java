package com.example.assignment.backend.Service;

import com.example.assignment.backend.Dto.DeviceDTO;
import com.example.assignment.backend.Dto.ShelfDTO;
import com.example.assignment.backend.Model.Shelf;

import java.util.List;

public interface ShelfService {
//    String createShelf(String shelfpositionId, ShelfDTO shelfDTO);

    String addShelfToPosition(String shelfpositionId, ShelfDTO shelfDTO);

    ShelfDTO getShelfById(String id);


    ShelfDTO updateShelf(String id, ShelfDTO shelfDTO);

    List<ShelfDTO> getAllShelves();

    boolean deleteShelf(String id);
}
