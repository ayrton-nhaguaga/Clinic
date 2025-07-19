package com.ayrton.clinic.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeSchedule {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
