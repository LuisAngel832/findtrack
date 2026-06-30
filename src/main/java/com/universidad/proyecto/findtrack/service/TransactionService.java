package com.universidad.proyecto.findtrack.service;

import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.universidad.proyecto.findtrack.dto.request.TransactionRequestDTO;
import com.universidad.proyecto.findtrack.dto.response.CategoryResponseDTO;
import com.universidad.proyecto.findtrack.dto.response.TransactionResponseDTO;
import com.universidad.proyecto.findtrack.model.Category;
import com.universidad.proyecto.findtrack.model.Transaction;
import com.universidad.proyecto.findtrack.model.TransactionType;
import com.universidad.proyecto.findtrack.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final CategoryService categoryService;

    private final TransactionRepository transactionRepository;

    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequest, UUID userId) {
        Category category = categoryService.getUsableCategory(transactionRequest.getCategoryId(), userId);

        TransactionType transactionType = TransactionType.valueOf(transactionRequest.getType());

        Transaction transaction = Transaction.builder()
                .userId(userId)
                .categoryId(category.getId())
                .amount(transactionRequest.getAmount())
                .type(transactionType)
                .description(transactionRequest.getDescription())
                .date(transactionRequest.getDate())
                .build();

        transactionRepository.save(transaction);

        CategoryResponseDTO categoryResponse = new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getIcon(),
                category.getType(),
                category.isDefault()
        );

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDescription(),
                transaction.getDate(),
                transaction.getCreatedAt(),
                categoryResponse
        );
    }

    public List<TransactionResponseDTO> getTransactions(UUID userId, TransactionType type, UUID categoryId, Integer year, Integer month) {
        List<Transaction> transactions = transactionRepository.findWithFilters(userId, type, categoryId, year, month);

        List<UUID> categoryIds = transactions.stream()
                .map(Transaction::getCategoryId)
                .distinct()
                .toList();

        List<Category> categories = categoryService.findCategoriesByIds(categoryIds);

        Map<UUID, Category> categoriesById = categories.stream()
                .collect(Collectors.toMap(Category::getId, Function.identity()));

        return transactions.stream()
            .map(transaction -> {
                Category category = categoriesById.get(transaction.getCategoryId());

                CategoryResponseDTO categoryResponse = new CategoryResponseDTO(
                    category.getId(),
                    category.getName(),
                    category.getIcon(),
                    category.getType(),
                    category.isDefault());

                return new TransactionResponseDTO(
                    transaction.getId(),
                    transaction.getAmount(),
                    transaction.getType(),
                    transaction.getDescription(),
                    transaction.getDate(),
                    transaction.getCreatedAt(),
                    categoryResponse);
            })
            .toList();
    }

}
