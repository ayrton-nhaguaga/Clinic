package com.ayrton.clinic.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("services")
@Data
public class Service {

    @Id
    private String id;
    private String name;
    private String description;
}
