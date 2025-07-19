package com.ayrton.clinic.repository;

import com.ayrton.clinic.enums.ReportType;
import com.ayrton.clinic.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {

    @Override
    Optional<Report> findById(String id);

    @Override
    List<Report> findAll();

    List<Report> findByType(ReportType type);

    List<Report> findByGeneratedAt(LocalDateTime generatedAt);
}
