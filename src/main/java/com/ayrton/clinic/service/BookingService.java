package com.ayrton.clinic.service;

import com.ayrton.clinic.dto.BookingDTO;
import com.ayrton.clinic.model.Booking;
import com.ayrton.clinic.model.Catalog;
import com.ayrton.clinic.model.Promotion;
import com.ayrton.clinic.model.ResourceUsage;
import com.ayrton.clinic.repository.BookingRepository;
import com.ayrton.clinic.repository.CatalogRepository;
import com.ayrton.clinic.enums.BookingStatus;
import com.ayrton.clinic.repository.PromotionRepository;
import com.ayrton.clinic.repository.ResourceUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    ResourceUsageRepository resourceUsageRepository;

    @Autowired
    PromotionRepository promotionRepository;

    public Booking createBooking(BookingDTO dto) {
        //Buscar o serviço (Catalog) para saber duração
        Catalog service = catalogRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Serviço não encontrado"));

        LocalDateTime start = dto.getAppointmentDate();
        LocalDateTime end = start.plusMinutes(service.getDurationMinutes());

        //Verificar conflito de agendamento com funcionário
        List<Booking> employeeConflicts = bookingRepository.findOverlappingBookings(
                dto.getEmployeeId(), start, end
        );
        if (!employeeConflicts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Funcionário já possui agendamento nesse intervalo.");
        }

        //Verificar se recursos estão disponíveis
        List<String> resourceIds = dto.getResourceIds() != null ? dto.getResourceIds() : List.of();
        for (String resourceId : resourceIds) {
            boolean conflict = resourceUsageRepository.existsByResourceIdAndStartTimeBeforeAndEndTimeAfter(
                    resourceId, end, start
            );
            if (conflict) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Recurso " + resourceId + " já está em uso nesse horário.");
            }
        }

        //Criar o Booking
        Booking booking = new Booking();
        booking.setClientId(dto.getClientId());
        booking.setEmployeeId(dto.getEmployeeId());
        booking.setServiceId(dto.getServiceId());
        booking.setAppointmentDate(start);
        booking.setEndTime(end);
        booking.setStatus(BookingStatus.AGENDADO);
        booking.setNotes(dto.getNotes());


        booking = bookingRepository.save(booking); // salvar primeiro para pegar o ID

        //Criar ResourceUsage para cada recurso
        for (String resourceId : resourceIds) {
            ResourceUsage usage = new ResourceUsage();
            usage.setBookingId(booking.getId());
            usage.setResourceId(resourceId);
            usage.setStartTime(start);
            usage.setEndTime(end);
            resourceUsageRepository.save(usage);
        }

        return booking;
    }


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getById(String id){
        return bookingRepository.findById(id);
    }

    public List<Booking> getByClientId(String clientId){
        return bookingRepository.findByClientId(clientId);
    }

    public List<Booking> getByEmployeeId(String employeeId){
        return  bookingRepository.findByEmployeeId(employeeId);
    }

    public List<Booking> getByAppointmentDate(LocalDateTime appointmentDate){
        return bookingRepository.findByAppointmentDate(appointmentDate);
    }

    public List<Booking> getByStatus(BookingStatus status){
        return bookingRepository.findByStatus(status);
    }

    public double applyPromotionToPrice(double price, String promotionCode) {
        List<Promotion> promotions = promotionRepository.findByCodeIgnoreCase(promotionCode);

        for (Promotion promotion : promotions) {
            if (promotion.isActive() && isValidNow(promotion)) {
                double discount = price * (promotion.getDiscountPercent() / 100.0);

                //se quiser apenas usar uma vez
                promotion.setActive(false);
                promotionRepository.save(promotion);

                return price - discount;
            }
        }

        // Nenhuma promoção válida encontrada
        return price;
    }

    private boolean isValidNow(Promotion promotion) {
        LocalDateTime now = LocalDateTime.now();
        return (promotion.getValidFrom().isBefore(now) || promotion.getValidFrom().isEqual(now)) &&
                (promotion.getValidTo().isAfter(now) || promotion.getValidTo().isEqual(now));
    }


    public Optional<Booking> updateBooking(BookingDTO dto) {
        Optional<Booking> existingOpt = bookingRepository.findById(dto.getId());

        if (existingOpt.isEmpty()) {
            return Optional.empty();
        }

        Booking existing = existingOpt.get();

        // Buscar o serviço atualizado
        Catalog service = catalogRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Serviço não encontrado"));

        LocalDateTime newStart = dto.getAppointmentDate();
        LocalDateTime newEnd = newStart.plusMinutes(service.getDurationMinutes());

        // Verifica conflito com outros agendamentos do mesmo funcionário (excluindo o atual)
        List<Booking> conflicts = bookingRepository.findOverlappingBookings(
                dto.getEmployeeId(), newStart, newEnd
        ).stream().filter(b -> !b.getId().equals(dto.getId())).toList();

        if (!conflicts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflito de horário com outro agendamento do funcionário.");
        }

        // Verifica conflito de recursos (excluindo o uso atual)
        List<String> resourceIds = dto.getResourceIds() != null ? dto.getResourceIds() : List.of();
        for (String resourceId : resourceIds) {
            boolean conflict = resourceUsageRepository.existsByResourceIdAndStartTimeBeforeAndEndTimeAfter(
                    resourceId, newEnd, newStart
            );

            // Verifica se o conflito não é apenas com os usos existentes deste próprio booking
            List<ResourceUsage> currentUsages = resourceUsageRepository.findAll()
                    .stream()
                    .filter(ru -> ru.getBookingId().equals(dto.getId()) && ru.getResourceId().equals(resourceId))
                    .toList();

            if (conflict && currentUsages.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Recurso " + resourceId + " já está em uso nesse horário.");
            }
        }

        // Atualizar booking
        existing.setClientId(dto.getClientId());
        existing.setEmployeeId(dto.getEmployeeId());
        existing.setServiceId(dto.getServiceId());
        existing.setAppointmentDate(newStart);
        existing.setEndTime(newEnd);
        existing.setNotes(dto.getNotes());
        existing.setStatus(dto.getStatus());

        Booking updated = bookingRepository.save(existing);

        // Atualizar usos de recursos
        // 1. Apagar os usos anteriores
        List<ResourceUsage> previousUsages = resourceUsageRepository.findAll()
                .stream().filter(ru -> ru.getBookingId().equals(dto.getId())).toList();
        resourceUsageRepository.deleteAll(previousUsages);

        // 2. Criar novos usos com novos horários
        for (String resourceId : resourceIds) {
            ResourceUsage usage = new ResourceUsage();
            usage.setBookingId(updated.getId());
            usage.setResourceId(resourceId);
            usage.setStartTime(newStart);
            usage.setEndTime(newEnd);
            resourceUsageRepository.save(usage);
        }

        return Optional.of(updated);
    }

    public boolean deleteBooking(String bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

        if (bookingOpt.isEmpty()) {
            return false;
        }

        // Apagar usos de recursos associados ao booking
        List<ResourceUsage> usages = resourceUsageRepository.findAll()
                .stream()
                .filter(usage -> usage.getBookingId().equals(bookingId))
                .toList();

        resourceUsageRepository.deleteAll(usages);

        // Apagar o próprio booking
        bookingRepository.deleteById(bookingId);

        return true;
    }

}

