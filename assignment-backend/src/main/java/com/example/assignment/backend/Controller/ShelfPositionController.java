package com.example.assignment.backend.Controller;


import com.example.assignment.backend.Dto.ShelfPositionDTO;
import com.example.assignment.backend.Model.ShelfPosition;
import com.example.assignment.backend.Service.ShelfPositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shelfPosition")
public class ShelfPositionController {

    private ShelfPositionService shelfPositionService;

    public ShelfPositionController(ShelfPositionService shelfPositionService) {
        this.shelfPositionService = shelfPositionService;
    }


    @GetMapping("/device/{deviceid}")
    public ResponseEntity<List<ShelfPositionDTO>> getAllShelfPositions(@PathVariable String deviceid ){
        List<ShelfPositionDTO> ShelfPositions=shelfPositionService.getShelfPositionByDeviceId(deviceid);
        return ShelfPositions.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(ShelfPositions);

    }
    @GetMapping("/{id}")
    public ResponseEntity<ShelfPositionDTO> getShelfPositionById(@PathVariable String id){
        ShelfPositionDTO ShelfPosition=shelfPositionService.getShelfPositionById(id);
        return ShelfPosition!=null? ResponseEntity.ok(ShelfPosition):ResponseEntity.noContent().build();
    }
}
