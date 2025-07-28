package com.ayrton.clinic.model;


import com.ayrton.clinic.enums.NotificationType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
public class Notification {

    @Id
    private String id;
    private String userId;
    private String title;
    private String message;
    private NotificationType type;
    private  boolean read = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}
