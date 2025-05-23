package com.personal.financial_dashboard_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.financial_dashboard_backend.model.Budget;
import com.personal.financial_dashboard_backend.model.User;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
	List<Budget> findByUser(User user);

	List<Budget> findByUserId(Long userId);

	List<Budget> findByCategoryId(Long categoryId);

	List<Budget> findByUserAndStartDateLessThanEqualAndEndDateGreaterThanEqual(User user, LocalDate now,
			LocalDate now2);

//    List<Budget> findByUserAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
//            User user, LocalDate currentDate, LocalDate currentDate2);
//    
//    List<Budget> findByUserAndCategoryId(User user, Long categoryId);
}
