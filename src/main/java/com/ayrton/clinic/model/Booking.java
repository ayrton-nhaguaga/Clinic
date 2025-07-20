package com.ayrton.clinic.model;

import com.ayrton.clinic.enums.BookingStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bookings")
@Data
public class Booking {

    @Id
    private String id;
    private String clientId;
    private String employeeId;
    private String serviceId;
    private LocalDateTime appointmentDate;
    private LocalDateTime endTime;
    private BookingStatus status;
    private String notes;
}
