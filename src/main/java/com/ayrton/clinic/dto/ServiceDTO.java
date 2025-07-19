package com.ayrton.clinic.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ServiceDTO {

    @NotNull
    private String id;

    @NotNull
    @Size(min = 0, max = 50)
    private String name;

    @NotNull
    @Size(min = 0, max = 400)
    private String description;
}
