package com.ayrton.clinic.service;

import com.ayrton.clinic.dto.ReportDTO;
import com.ayrton.clinic.enums.BookingStatus;
import com.ayrton.clinic.enums.ReportType;
import com.ayrton.clinic.model.*;
import com.ayrton.clinic.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayrton.clinic.enums.EmployeeSpeciality;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final EmployeeRepository employeeRepository;
    private final ResourceUsageRepository resourceUsageRepository;
    private final BookingRepository bookingRepository;
    private final ResourceRepository resourceRepository;

    public ReportDTO generateReport(ReportType type) {
        if (type == ReportType.RECEITA) {
            throw new UnsupportedOperationException("Relatório do tipo RECEITA não é suportado por este serviço.");
        }

        Map<String, Object> reportData;

        switch (type) {
            case FUNCIONARIOS -> reportData = generateEmployeeReport();
            case OCUPACAO -> reportData = generateOcupacaoReport();
            case GERAL -> reportData = generateGeralReport();
            default -> throw new IllegalArgumentException("Tipo de relatório inválido.");
        }

        Report report = new Report();
        report.setType(type);
        report.setGeneratedAt(LocalDateTime.now());
        report.setData(reportData);

        report = reportRepository.save(report);
        return toDTO(report);
    }

    private Map<String, Object> generateEmployeeReport() {
        Map<String, Object> data = new HashMap<>();
        List<Employee> allEmployees = employeeRepository.findAll();

        LocalDateTime inicioDoMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime fimDoMes = inicioDoMes.plusMonths(1);

        // Considera ativo se tiver horário com endTime dentro do mês atual
        long ativos = allEmployees.stream()
                .filter(emp -> emp.getSchedules() != null && emp.getSchedules().stream()
                        .anyMatch(schedule ->
                                schedule.getEndTime().isAfter(inicioDoMes) &&
                                        schedule.getStartTime().isBefore(fimDoMes)))
                .count();

        int total = allEmployees.size();
        long inativos = total - ativos;

        // Média de rating dos que trabalharam no mês
        double avgRating = allEmployees.stream()
                .filter(emp -> emp.getSchedules() != null && emp.getSchedules().stream()
                        .anyMatch(schedule ->
                                schedule.getEndTime().isAfter(inicioDoMes) &&
                                        schedule.getStartTime().isBefore(fimDoMes)))
                .mapToDouble(Employee::getRating)
                .average()
                .orElse(0.0);

        // Especialidades dos que atuaram no mês
        Map<String, Long> especialidades = Arrays.stream(EmployeeSpeciality.values())
                .collect(Collectors.toMap(
                        Enum::name,
                        esp -> allEmployees.stream()
                                .filter(emp -> emp.getSpeciality().contains(esp) &&
                                        emp.getSchedules() != null && emp.getSchedules().stream()
                                        .anyMatch(schedule ->
                                                schedule.getEndTime().isAfter(inicioDoMes) &&
                                                        schedule.getStartTime().isBefore(fimDoMes)))
                                .count()
                ));

        data.put("mesReferencia", inicioDoMes.getMonth().toString());
        data.put("totalFuncionarios", total);
        data.put("ativosNoMes", ativos);
        data.put("inativosNoMes", inativos);
        data.put("mediaAvaliacaoNoMes", avgRating);
        data.put("especialidadesNoMes", especialidades);

        return data;
    }


    private Map<String, Object> generateOcupacaoReport() {
        Map<String, Object> data = new HashMap<>();

        LocalDateTime inicioDoMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime fimDoMes = inicioDoMes.plusMonths(1);

        // --- 1. Funcionário mais ocupado ---
        List<Booking> bookings = bookingRepository.findByAppointmentDateBetweenAndStatus(
                inicioDoMes, fimDoMes, BookingStatus.CONCLUIDO);

        Map<String, Long> tempoPorFuncionario = new HashMap<>();

        for (Booking booking : bookings) {
            if (booking.getEmployeeId() != null && booking.getEndTime() != null && booking.getAppointmentDate() != null) {
                long minutos = java.time.Duration.between(booking.getAppointmentDate(), booking.getEndTime()).toMinutes();
                tempoPorFuncionario.merge(booking.getEmployeeId(), minutos, Long::sum);
            }
        }

        String funcionarioMaisOcupadoId = tempoPorFuncionario.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        long tempoFuncionarioMaisOcupado = funcionarioMaisOcupadoId != null ? tempoPorFuncionario.get(funcionarioMaisOcupadoId) : 0;

        // --- 2. Recurso mais utilizado ---
        List<ResourceUsage> usos = resourceUsageRepository.findByStartTimeBetween(inicioDoMes, fimDoMes);

        Map<String, Long> tempoPorRecurso = new HashMap<>();

        for (ResourceUsage usage : usos) {
            if (usage.getStartTime() != null && usage.getEndTime() != null) {
                long minutos = java.time.Duration.between(usage.getStartTime(), usage.getEndTime()).toMinutes();
                tempoPorRecurso.merge(usage.getResourceId(), minutos, Long::sum);
            }
        }

        String recursoMaisUsadoId = tempoPorRecurso.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        long tempoRecursoMaisUsado = recursoMaisUsadoId != null ? tempoPorRecurso.get(recursoMaisUsadoId) : 0;

        // --- 3. Ocupação total de recursos ---
        List<Resource> recursosAtivos = resourceRepository.findByActiveTrue();
        int totalRecursos = recursosAtivos.size();

        // Tempo total possível (considerando recursos disponíveis em tempo integral no mês)
        long minutosTotaisMes = java.time.Duration.between(inicioDoMes, fimDoMes).toMinutes();
        long capacidadeTotalMinutos = minutosTotaisMes * totalRecursos;

        long tempoTotalUsado = tempoPorRecurso.values().stream().mapToLong(Long::longValue).sum();

        double ocupacaoPercentual = capacidadeTotalMinutos > 0
                ? (tempoTotalUsado * 100.0 / capacidadeTotalMinutos)
                : 0.0;

        data.put("mesReferencia", inicioDoMes.getMonth().toString());
        data.put("funcionarioMaisOcupadoId", funcionarioMaisOcupadoId);
        data.put("tempoFuncionarioMaisOcupadoMin", tempoFuncionarioMaisOcupado);
        data.put("recursoMaisUtilizadoId", recursoMaisUsadoId);
        data.put("tempoRecursoMaisUtilizadoMin", tempoRecursoMaisUsado);
        data.put("totalRecursosAtivos", totalRecursos);
        data.put("ocupacaoPercentual", String.format("%.2f%%", ocupacaoPercentual));

        return data;
    }

    private Map<String, Object> generateGeralReport() {
        Map<String, Object> data = new HashMap<>();

        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime inicioDoDia = hoje.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime fimDoDia = inicioDoDia.plusDays(1);

        LocalDateTime inicioDoMes = hoje.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime fimDoMes = inicioDoMes.plusMonths(1);

        // --- Pacientes atendidos hoje ---
        long pacientesHoje = bookingRepository.countByAppointmentDateBetweenAndStatus(
                inicioDoDia, fimDoDia, BookingStatus.CONCLUIDO);

        // --- Consultas realizadas no mês ---
        long consultasRealizadas = bookingRepository.countByAppointmentDateBetweenAndStatus(
                inicioDoMes, fimDoMes, BookingStatus.CONCLUIDO);

        // --- Cancelamentos no mês ---
        long cancelamentos = bookingRepository.countByAppointmentDateBetweenAndStatus(
                inicioDoMes, fimDoMes, BookingStatus.CANCELADO);

        data.put("mesReferencia", inicioDoMes.getMonth().toString());
        data.put("pacientesHoje", pacientesHoje);
        data.put("consultasRealizadas", consultasRealizadas);
        data.put("cancelamentos", cancelamentos);

        return data;
    }

    private ReportDTO toDTO(Report report) {
        ReportDTO dto = new ReportDTO();
        dto.setId(report.getId());
        dto.setType(report.getType());
        dto.setGeneratedAt(report.getGeneratedAt());
        dto.setData(report.getData());
        return dto;
    }
}

