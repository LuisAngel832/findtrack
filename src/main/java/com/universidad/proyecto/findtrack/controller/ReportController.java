package com.universidad.proyecto.findtrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.proyecto.findtrack.dto.response.MonthlySummaryDTO;
import com.universidad.proyecto.findtrack.security.UserPrincipal;
import com.universidad.proyecto.findtrack.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/summary")
    public ResponseEntity<MonthlySummaryDTO> getMonthlySummary(
            @RequestParam Integer month,
            @RequestParam Integer year,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        MonthlySummaryDTO summary = reportService.getMonthlySummary(userPrincipal.getId(), year, month);
        return ResponseEntity.ok(summary);
    }

}
