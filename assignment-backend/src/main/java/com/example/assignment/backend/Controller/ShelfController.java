package com.example.assignment.backend.Controller;

import com.example.assignment.backend.Dto.ShelfDTO;
import com.example.assignment.backend.Service.ShelfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/shelves")
public class ShelfController {
    private final ShelfService shelfService;

    public  ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }


    @PostMapping("/create/{positionId}")
    public ResponseEntity<String> addShelf(@PathVariable String positionId, @RequestBody ShelfDTO shelfDTO) {
        String shelfId = shelfService.addShelfToPosition(positionId, shelfDTO);
        return ResponseEntity.ok("Shelf created successfully with id: " + shelfId);
    }


  @GetMapping("/{id}")
  public ResponseEntity<ShelfDTO> getShelfById(@PathVariable String id) {
        ShelfDTO shelf = shelfService.getShelfById(id);
        if (shelf != null)
        {
            return ResponseEntity.ok(shelf);
        } return ResponseEntity.notFound().build();
    }



    @GetMapping
    public ResponseEntity<List<ShelfDTO>> getAllShelves() {
        return ResponseEntity.ok(shelfService.getAllShelves());
    }



@PutMapping("/{id}")
public ResponseEntity<ShelfDTO> updateShelf(@PathVariable String id, @RequestBody ShelfDTO shelfDTO) {
    ShelfDTO updatedShelf = shelfService.updateShelf(id, shelfDTO);
    if (updatedShelf != null) {
        return ResponseEntity.ok(updatedShelf);
    } return ResponseEntity.notFound().build(); }



@DeleteMapping("/{id}")
public ResponseEntity<String> deleteShelf(@PathVariable String id) {
    boolean deleted = shelfService.deleteShelf(id);
    if (deleted) {
        return ResponseEntity.ok("Shelf deleted successfully");
    } return ResponseEntity.notFound().build();
}
}