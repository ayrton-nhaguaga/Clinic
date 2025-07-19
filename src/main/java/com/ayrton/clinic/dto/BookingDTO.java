package com.ayrton.clinic.dto;

import com.ayrton.clinic.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDTO {

    @NotNull
    private String id;

    @NotNull
    private String clientId;

    @NotNull
    private String employeeId;

    @NotNull
    private String serviceId;

    @NotNull
    private LocalDateTime appointmentDate;

    @NotNull
    private BookingStatus status;

    @Size(min = 0, max = 750)
    private String notes;
}
