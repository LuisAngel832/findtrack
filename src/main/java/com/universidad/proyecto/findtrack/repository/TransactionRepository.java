package com.universidad.proyecto.findtrack.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.universidad.proyecto.findtrack.dto.CategorySpent;
import com.universidad.proyecto.findtrack.model.Transaction;
import com.universidad.proyecto.findtrack.model.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

        @Query("SELECT t FROM Transaction t WHERE t.userId = :userId " +
                        "AND (:type IS NULL OR t.type = :type) " +
                        "AND (:categoryId IS NULL OR t.categoryId = :categoryId) " +
                        "AND (:year IS NULL OR EXTRACT(YEAR FROM t.date) = :year) " +
                        "AND (:month IS NULL OR EXTRACT(MONTH FROM t.date) = :month)")
        List<Transaction> findWithFilters(@Param("userId") UUID userId,
                        @Param("type") TransactionType type,
                        @Param("categoryId") UUID categoryId,
                        @Param("year") Integer year,
                        @Param("month") Integer month);

        @Query("SELECT new com.universidad.proyecto.findtrack.dto.CategorySpent(t.categoryId, SUM(t.amount)) " +
                        "FROM Transaction t " +
                        "WHERE t.userId = :userId " +
                        "AND t.type = 'expense' " +
                        "AND t.date >= :inicioMes " +
                        "AND t.date < :inicioMesSiguiente " +
                        "GROUP BY t.categoryId")
        List<CategorySpent> findSpentByMonth(@Param("userId") UUID userId,
                        @Param("inicioMes") LocalDate inicioMes,
                        @Param("inicioMesSiguiente") LocalDate inicioMesSiguiente);

        @Query("SELECT COALESCE(SUM(t.amount), cast(0 as big_decimal)) FROM Transaction t " +
                        "WHERE t.userId = :userId " +
                        "AND t.type = :type " +
                        "AND t.date >= :inicioMes " +
                        "AND t.date < :inicioMesSiguiente")
        BigDecimal sumAmountByUserAndTypeAndDate(@Param("userId") UUID userId, @Param("type") TransactionType type,
                        @Param("inicioMes") LocalDate inicioMes,
                        @Param("inicioMesSiguiente") LocalDate inicioMesSiguiente);

        @Query("SELECT COUNT(t) FROM Transaction t " +
                        "WHERE t.userId = :userId " +
                        "AND t.date >= :inicioMes " +
                        "AND t.date < :inicioMesSiguiente")
        Long countTransactionsByUserAndDate(@Param("userId") UUID userId,
                        @Param("inicioMes") LocalDate inicioMes,
                        @Param("inicioMesSiguiente") LocalDate inicioMesSiguiente);
}
