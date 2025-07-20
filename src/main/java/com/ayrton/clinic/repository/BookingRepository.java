package com.ayrton.clinic.repository;

import com.ayrton.clinic.enums.BookingStatus;
import com.ayrton.clinic.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {

    @Override
    Optional<Booking> findById(String id);

    @Override
    List<Booking> findAll();

    List<Booking> findByClientId(String clientId);

    List<Booking> findByEmployeeId(String employeeId);

    List<Booking> findByAppointmentDate(LocalDateTime appointmentDate);

    List<Booking> findByStatus(BookingStatus status);

    // Verifica se há sobreposição
    @Query("{ 'employeeId': ?0, 'appointmentDate': { $lt: ?2 }, 'endTime': { $gt: ?1 } }")
    List<Booking> findOverlappingBookings(String employeeId, LocalDateTime start, LocalDateTime end);
}
