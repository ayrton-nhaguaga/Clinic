package com.ayrton.clinic.model;

import com.ayrton.clinic.enums.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
@Data
public class User {

    @Id
    private String id;
    private String name;
    private String password;
    private boolean active = true;
    private Role role;
    private UserProfile profile;
    private LocalDateTime createdAt = LocalDateTime.now();
}
