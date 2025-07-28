package com.ayrton.clinic.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackDTO {


    @NotNull
    private String userId;

    @NotNull
    private String bookingId;

    @Size(min = 1, max = 5)
    private int rating; // de 1 a 5

    @Size(min = 0, max = 150)
    private String comment;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();
}
