package com.ayrton.clinic.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResourceDTO {

    @NotNull
    private String id;

    @NotNull
    @Size(min = 0, max = 50)
    private String name;

    @NotNull
    @Size(min = 0, max = 50)
    private String type;

    @NotNull
    private boolean active;
}
