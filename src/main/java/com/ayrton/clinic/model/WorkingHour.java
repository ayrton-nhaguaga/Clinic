package com.ayrton.clinic.model;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class WorkingHour {
    private DayOfWeek dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;
}
