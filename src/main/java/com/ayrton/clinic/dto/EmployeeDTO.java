package com.ayrton.clinic.dto;

import com.ayrton.clinic.enums.EmployeeSpeciality;
import com.ayrton.clinic.model.EmployeeSchedule;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeDTO {
    @NotNull
    private String id;

    @NotNull
    @Size(min = 0, max = 100)
    private String name;

    @NotNull
    @Size(min = 0, max = 200)
    private String email;

    @NotNull
    @Size(min = 0, max = 50)
    private String phone;

    private double rating;
    private List<EmployeeSchedule> schedules = new ArrayList<>();
    private List<EmployeeSpeciality> speciality = new ArrayList<>();
}
