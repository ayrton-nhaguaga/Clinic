package com.ayrton.clinic.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CatalogDTO {



    @NotNull
    @Size(min = 0, max = 75)
    private String name;

    @NotNull
    @Size(min = 0, max = 500)
    private String description;

    @NotNull
    private double price;

    @NotNull
    private int durationMinutes;
}
