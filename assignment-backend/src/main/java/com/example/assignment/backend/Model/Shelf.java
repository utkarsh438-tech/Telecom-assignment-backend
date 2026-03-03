package com.example.assignment.backend.Model;

import jakarta.validation.constraints.NotBlank;

public class Shelf {

    @NotBlank
    private String id;
    @NotBlank
    private String shelfName;
    @NotBlank
     private String partNumber;

    private boolean isDeleted=false;

}
