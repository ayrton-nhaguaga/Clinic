package com.ayrton.clinic.repository;

import com.ayrton.clinic.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String> {

    @Override
    Optional<Feedback> findById(String id);

    @Override
    List<Feedback> findAll();

    List<Feedback> findByUserId(String userId);

    Optional<Feedback> findByBookingId(String bookingId);

    List<Feedback> findByRating(int rating);

    List<Feedback> findByCreatedAt(LocalDateTime createdAt);
}
