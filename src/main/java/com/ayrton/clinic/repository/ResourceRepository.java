package com.ayrton.clinic.repository;

import com.ayrton.clinic.model.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends MongoRepository<Resource, String> {

    @Override
    Optional<Resource> findById(String id);

    @Override
    List<Resource> findAll();

    List<Resource> findByNameIgnoreCase(String name);

    List<Resource> findByTypeIgnoreCase(String type);

    List<Resource> findByActive(boolean active);

    List<Resource> findByActiveTrue();

}
