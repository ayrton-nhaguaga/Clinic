package com.ayrton.clinic.dto;

import com.ayrton.clinic.enums.NotificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {

    @NotNull
    private String id;

    @NotNull
    private String userId;

    @NotNull
    @Size(min = 0, max = 50)
    private String title;

    @NotNull
    @Size(min = 0, max = 500)
    private String message;

    @NotNull
    private NotificationType type;

    @NotNull
    @Email
    private String userEmail;

    private  boolean read = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}
