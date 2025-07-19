package com.ayrton.clinic.repository;

import com.ayrton.clinic.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Override
    Optional<Product> findById(String id);

    @Override
    List<Product> findAll();

    List<Product> findByNameIgnoreCase(String name);

    List<Product> findByQuantity(int quantity);

    List<Product> findByLastRestockDate(LocalDateTime lastRestockDate);
}
