package com.ayrton.clinic.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "resource_usages")
@Data
public class ResourceUsage {

    @Id
    private String id;
    private String resourceId;
    private String bookingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
