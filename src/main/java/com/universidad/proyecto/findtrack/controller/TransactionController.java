package com.universidad.proyecto.findtrack.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.universidad.proyecto.findtrack.dto.request.TransactionRequestDTO;
import com.universidad.proyecto.findtrack.dto.response.TransactionResponseDTO;
import com.universidad.proyecto.findtrack.model.TransactionType;
import com.universidad.proyecto.findtrack.security.UserPrincipal;
import com.universidad.proyecto.findtrack.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity
                .ok(transactionService.getTransactions(userPrincipal.getId(), type, categoryId, year, month));
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid TransactionRequestDTO transactionRequest) {

        TransactionResponseDTO createdTransaction = transactionService.createTransaction(transactionRequest, userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }
}
