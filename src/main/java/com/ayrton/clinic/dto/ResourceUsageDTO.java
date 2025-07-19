package com.ayrton.clinic.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResourceUsageDTO {

    @NotNull
    private String id;

    @NotNull
    private String resourceId;

    @NotNull
    private String bookingId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;
}
