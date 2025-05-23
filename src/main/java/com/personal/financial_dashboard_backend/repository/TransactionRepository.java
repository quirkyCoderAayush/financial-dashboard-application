package com.personal.financial_dashboard_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personal.financial_dashboard_backend.model.Transaction;
import com.personal.financial_dashboard_backend.model.TransactionType;
import com.personal.financial_dashboard_backend.model.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("SELECT t FROM Transaction t WHERE t.user = :user ORDER BY t.transactionDate DESC")
	Page<Transaction> findByUser(@Param("user") User user, Pageable pageable);

	List<Transaction> findByUserId(Long userId);

	List<Transaction> findByCategoryId(Long categoryId);

	List<Transaction> findByUserOrderByTransactionDateDesc(User user);

	List<Transaction> findByUserAndCategoryId(User user, Long categoryId);

	@Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.type = :type")
	List<Transaction> findByUserAndType(@Param("user") User user, @Param("type") TransactionType type);

	List<Transaction> findByUserAndTransactionDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

}
