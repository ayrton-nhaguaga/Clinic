package com.ayrton.clinic.repository;

import com.ayrton.clinic.model.ResourceUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceUsageRepository extends MongoRepository<ResourceUsage, String> {

    @Override
    Optional<ResourceUsage> findById(String id);

    @Override
    List<ResourceUsage> findAll();

    Optional<ResourceUsage> findByResourceId(String resourceId);

    Optional<ResourceUsage> findByBookingId(String bookingId);

    List<ResourceUsage> findByStartTime(LocalDateTime startTime);

    List<ResourceUsage> findByEndTime(LocalDateTime endTime);
}
