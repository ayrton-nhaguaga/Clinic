package com.ayrton.clinic.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDTO {
    @NotNull
    private String id;

    @NotNull
    @Size(min = 0, max = 100)
    private String name;

    @Size(min = 0, max = 350)
    private String description;

    @NotNull
    private int quantity;

    @NotNull
    private String unit;

    @NotNull
    private LocalDateTime lastRestockDate;
}
