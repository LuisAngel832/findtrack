package com.universidad.proyecto.findtrack.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.proyecto.findtrack.common.DateRange;
import com.universidad.proyecto.findtrack.common.DateRangeUtil;
import com.universidad.proyecto.findtrack.dto.response.DailyTimelineResponseDTO;
import com.universidad.proyecto.findtrack.dto.response.ExpensesByCategoryResponseDTO;
import com.universidad.proyecto.findtrack.dto.response.MonthlySummaryDTO;
import com.universidad.proyecto.findtrack.model.TransactionType;
import com.universidad.proyecto.findtrack.security.UserPrincipal;
import com.universidad.proyecto.findtrack.service.ReportService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
@Validated
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

    @GetMapping("/by-category")
    public ResponseEntity<List<ExpensesByCategoryResponseDTO>> getExpensesByCategory(
            @RequestParam  @Min(1) @Max(12) int month,
            @RequestParam @Min(2000) @Max(2100) int year,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        DateRange dateRange = DateRangeUtil.getDateRange(YearMonth.of(year, month));
        TransactionType type = TransactionType.EXPENSE;

        List<ExpensesByCategoryResponseDTO> expenses = reportService.expensesByCategory(userPrincipal.getId(), dateRange, type);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<DailyTimelineResponseDTO>> getTimeline(
            @RequestParam  @Min(1) @Max(12) int month,
            @RequestParam @Min(2000) @Max(2100) int year,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        YearMonth yearMonth = YearMonth.of(year, month);

        List<DailyTimelineResponseDTO> timeline = reportService.getMonthlyTimeline(userPrincipal.getId(), yearMonth);
        return ResponseEntity.ok(timeline);
    }
    

}
