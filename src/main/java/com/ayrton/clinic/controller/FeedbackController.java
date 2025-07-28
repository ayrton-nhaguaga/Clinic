package com.ayrton.clinic.controller;

import com.ayrton.clinic.dto.FeedbackDTO;
import com.ayrton.clinic.model.Feedback;
import com.ayrton.clinic.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody FeedbackDTO dto){
        Feedback feedback = feedbackService.createFeedback(dto);
        return new ResponseEntity<>(feedback, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks(){
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks,HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Feedback>> getById(@PathVariable String id){
        Optional<Feedback> feedback = feedbackService.getById(id);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<List<Feedback>> getByUserId(@RequestParam String userId){
        List<Feedback> feedbacks = feedbackService.getByUserId(userId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/booking-id/{bookingId}")
    public ResponseEntity<Optional<Feedback>> getByBookingId(@RequestParam String bookingId){
        Optional<Feedback> feedback = feedbackService.getByBookingId(bookingId);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Feedback>> getByRating(@RequestParam int rating){
        List<Feedback> feedbacks = feedbackService.getByRating(rating);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/created-at/{createdAt}")
    public ResponseEntity<List<Feedback>> getByCreatedAt(@RequestParam LocalDateTime createdAt){
        List<Feedback> feedbacks = feedbackService.getByCreatedAt(createdAt);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteFeedbackByBooking(@PathVariable String id) {
        boolean deleted = feedbackService.deleteFeedback(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }
}
