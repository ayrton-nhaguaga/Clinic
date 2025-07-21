package com.ayrton.clinic.service;

import com.ayrton.clinic.dto.ReportDTO;
import com.ayrton.clinic.enums.ReportType;
import com.ayrton.clinic.model.Report;
import com.ayrton.clinic.repository.EmployeeRepository;
import com.ayrton.clinic.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayrton.clinic.enums.EmployeeSpeciality;
import com.ayrton.clinic.model.Employee;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    @Autowired
    private final ReportRepository reportRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

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
        // TODO: lógica real aqui
        data.put("salasOcupadas", 3);
        data.put("salasLivres", 2);
        data.put("ocupacaoPercentual", "60%");
        return data;
    }

    private Map<String, Object> generateGeralReport() {
        Map<String, Object> data = new HashMap<>();
        // TODO: lógica real aqui
        data.put("pacientesHoje", 25);
        data.put("consultasRealizadas", 40);
        data.put("cancelamentos", 5);
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

