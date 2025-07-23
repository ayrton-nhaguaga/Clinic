package com.ayrton.clinic.controller;

import com.ayrton.clinic.dto.ReportDTO;
import com.ayrton.clinic.enums.ReportType;
import com.ayrton.clinic.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{type}")
    public ResponseEntity<ReportDTO> generateReport(@PathVariable ReportType type) {
        try {
            ReportDTO report = reportService.generateReport(type);
            return ResponseEntity.ok(report);
        } catch (UnsupportedOperationException e) {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(null);
        }
    }
}
