package com.ayrton.clinic.controller;

import com.ayrton.clinic.dto.BookingDTO;
import com.ayrton.clinic.enums.BookingStatus;
import com.ayrton.clinic.model.Booking;
import com.ayrton.clinic.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO dto){
        Booking booking = bookingService.createBooking(dto);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings(){
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Booking>> getById(@RequestParam String id){
        Optional<Booking> booking = bookingService.getById(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("/client-id")
    public ResponseEntity<List<Booking>> getByClientId(@RequestParam String clientId){
        List<Booking> bookings = bookingService.getByClientId(clientId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/employee-id")
    public ResponseEntity<List<Booking>> getByEmployeeId(@RequestParam String employeeId){
        List<Booking> bookings = bookingService.getByEmployeeId(employeeId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/appointment-date")
    public ResponseEntity<List<Booking>> getByAppointmentDate(@RequestParam LocalDateTime appointmentDate){
        List<Booking> bookings = bookingService.getByAppointmentDate(appointmentDate);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<List<Booking>> getByStatus(@RequestParam BookingStatus status){
        List<Booking> bookings = bookingService.getByStatus(status);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/price-promotion")
    public ResponseEntity<Double> pricePromotion(@RequestParam double price, @RequestParam String promotionCode){
        double finalPrice = bookingService.applyPromotionToPrice(price, promotionCode);
        return new ResponseEntity<>(finalPrice, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable String id, @RequestBody BookingDTO dto) {
        dto.setId(id); // garante que o ID da URL seja usado
        Optional<Booking> updated = bookingService.updateBooking(dto);

        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        boolean deleted = bookingService.deleteBooking(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }


}
