package com.ayrton.clinic.repository;

import com.ayrton.clinic.model.Catalog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogRepository extends MongoRepository<Catalog, String> {

    @Override
    Optional<Catalog> findById(String id);

    @Override
    List<Catalog> findAll();

    List<Catalog> findByNameIgnoreCase(String name);

    List<Catalog> findByPrice(double price);

    List<Catalog> findByDurationMinutes(int durationMinutes);
}
