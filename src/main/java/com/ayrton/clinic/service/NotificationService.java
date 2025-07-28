package com.ayrton.clinic.service;

import com.ayrton.clinic.dto.NotificationDTO;
import com.ayrton.clinic.model.Notification;
import com.ayrton.clinic.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationRepository repository;

    public NotificationDTO create(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setUserId(dto.getUserId());
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        notification.setType(dto.getType());
        notification.setRead(dto.isRead());

        Notification saved = repository.save(notification);

        // Enviar e-mail automaticamente
        if (dto.getUserEmail() != null && !dto.getUserEmail().isEmpty()) {
            emailService.sendEmail(
                    dto.getUserEmail(),
                    dto.getTitle(),
                    dto.getMessage()
            );
        }

        return toDTO(saved);
    }


    public void markAsRead(String id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        notification.setRead(true);
        repository.save(notification);
    }

    private NotificationDTO toDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setRead(notification.isRead());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }

    public List<Notification> getByUserId(String userId){
        return repository.findByUserId(userId);
    }
}

