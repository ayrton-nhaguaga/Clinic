package com.ayrton.clinic.dto;

import com.ayrton.clinic.enums.Role;
import com.ayrton.clinic.model.UserProfile;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    @NotNull
    private String id;

    @NotNull
    @Size(min = 0, max = 50)
    private String name;

    @NotNull
    @Size(min = 0, max = 10)
    private String password;

    @NotNull
    private boolean active = true;

    @NotNull
    private Role role;

    @NotNull
    private UserProfile profile;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();
}
