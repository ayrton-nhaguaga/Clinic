package com.ayrton.clinic.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products")
@Data
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private int quantity;
    private String unit;
    private LocalDateTime lastRestockDate;
}
