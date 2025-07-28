package com.ayrton.clinic.controller;

import com.ayrton.clinic.dto.NotificationDTO;
import com.ayrton.clinic.enums.NotificationType;
import com.ayrton.clinic.model.Notification;
import com.ayrton.clinic.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationDTO> create(@RequestBody NotificationDTO dto) {
        NotificationDTO created = notificationService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getByUser(@PathVariable String userId) {
        List<Notification> notifications = notificationService.getByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable String id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}


