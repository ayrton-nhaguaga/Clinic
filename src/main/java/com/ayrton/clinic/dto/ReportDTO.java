package com.ayrton.clinic.dto;

import com.ayrton.clinic.enums.ReportType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ReportDTO {

    @NotNull
    private String id;

    @NotNull
    private ReportType type;

    @NotNull
    private LocalDateTime generatedAt;

    @NotNull
    private Map<String, Object> data;
}
