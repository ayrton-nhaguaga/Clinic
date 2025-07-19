package com.ayrton.clinic.repository;

import com.ayrton.clinic.model.Service;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends MongoRepository<Service, String> {

    @Override
    Optional<Service> findById(String id);

    @Override
    List<Service> findAll();

    List<Service> findByNameIgnoreCase(String name);
}
