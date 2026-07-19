package com.universidad.proyecto.findtrack.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.proyecto.findtrack.dto.request.BudgetRequestDTO;
import com.universidad.proyecto.findtrack.dto.request.BudgetUpdateRequestDTO;
import com.universidad.proyecto.findtrack.dto.response.BudgetResponseDTO;
import com.universidad.proyecto.findtrack.security.UserPrincipal;
import com.universidad.proyecto.findtrack.service.BudgetService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public ResponseEntity<List<BudgetResponseDTO>> getBudgets(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Integer month, @RequestParam Integer year) {
        return ResponseEntity.ok(budgetService.getBudgets(userPrincipal.getId(), month, year));
    }

    @PostMapping
    public ResponseEntity<BudgetResponseDTO> createBudget(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid BudgetRequestDTO budgetRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(budgetService.createBudget(userPrincipal.getId(), budgetRequestDTO));
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<BudgetResponseDTO> updateBudget(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID budgetId, @RequestBody @Valid BudgetUpdateRequestDTO budgetRequestDTO) {
        return ResponseEntity.ok(budgetService.updateBudget(budgetId, userPrincipal.getId(), budgetRequestDTO));
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteBudget(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID budgetId) {
        budgetService.deleteBudget(budgetId, userPrincipal.getId());
        return ResponseEntity.noContent().build();
    }
    
}
