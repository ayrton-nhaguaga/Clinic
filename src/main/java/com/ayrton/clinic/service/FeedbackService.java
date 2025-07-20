package com.ayrton.clinic.service;

import com.ayrton.clinic.dto.FeedbackDTO;
import com.ayrton.clinic.model.Feedback;
import com.ayrton.clinic.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback createFeedback(FeedbackDTO dto){
        Feedback feedback = new Feedback();
        feedback.setId(dto.getId());
        feedback.setUserId(dto.getUserId());
        feedback.setBookingId(dto.getBookingId());
        feedback.setRating(dto.getRating());
        feedback.setComment(dto.getComment());
        feedback.setCreatedAt(dto.getCreatedAt());
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacks(){
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> getById(String id){
        return feedbackRepository.findById(id);
    }

    public List<Feedback> getByUserId(String userId){
        return feedbackRepository.findByUserId(userId);
    }

    public Optional<Feedback> getByBookingId(String bookingId){
        return feedbackRepository.findByBookingId(bookingId);
    }

    public List<Feedback> getByRating(int rating){
        return feedbackRepository.findByRating(rating);
    }

    public List<Feedback> getByCreatedAt(LocalDateTime createdAt){
        return feedbackRepository.findByCreatedAt(createdAt);
    }

    public boolean deleteFeedback(String bookingId){
        Optional<Feedback> feedback = feedbackRepository.findByBookingId(bookingId);

        if (!feedback.isEmpty()){
            feedbackRepository.delete(feedback.get());
            return true;
        }
        return false;
    }
}
