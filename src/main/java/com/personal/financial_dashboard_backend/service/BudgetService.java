package com.personal.financial_dashboard_backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.financial_dashboard_backend.dto.BudgetDTO;
import com.personal.financial_dashboard_backend.model.Budget;
import com.personal.financial_dashboard_backend.model.Category;
import com.personal.financial_dashboard_backend.model.Transaction;
import com.personal.financial_dashboard_backend.model.TransactionType;
import com.personal.financial_dashboard_backend.model.User;
import com.personal.financial_dashboard_backend.repository.BudgetRepository;
import com.personal.financial_dashboard_backend.repository.CategoryRepository;
import com.personal.financial_dashboard_backend.repository.TransactionRepository;
import com.personal.financial_dashboard_backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	public List<BudgetDTO> getAllBudgetsByUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		return budgetRepository.findByUser(user).stream()
				.map(budget -> convertToDTO(budget, calculateSpentAmount(budget))).collect(Collectors.toList());
	}

	public List<BudgetDTO> getActiveBudgets(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		LocalDate now = LocalDate.now();

		return budgetRepository.findByUserAndStartDateLessThanEqualAndEndDateGreaterThanEqual(user, now, now).stream()
				.map(budget -> convertToDTO(budget, calculateSpentAmount(budget))).collect(Collectors.toList());
	}

	@Transactional
	public BudgetDTO createBudget(Long userId, BudgetDTO budgetDTO) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Category category = null;
		if (budgetDTO.getCategoryId() != null) {
			category = categoryRepository.findById(budgetDTO.getCategoryId())
					.orElseThrow(() -> new RuntimeException("Category not found"));
		}

		Budget budget = new Budget();
		budget.setName(budgetDTO.getName());
		budget.setDescription(budgetDTO.getDescription());
		budget.setAmount(budgetDTO.getAmount());
		budget.setStartDate(budgetDTO.getStartDate());
		budget.setEndDate(budgetDTO.getEndDate());
		budget.setCategory(category);
		budget.setUser(user);

		Budget savedBudget = budgetRepository.save(budget);
		return convertToDTO(savedBudget, BigDecimal.ZERO);
	}

	@Transactional
	public BudgetDTO updateBudget(Long userId, Long budgetId, BudgetDTO budgetDTO) {
		Budget budget = budgetRepository.findById(budgetId).orElseThrow(() -> new RuntimeException("Budget not found"));

		// Check if the budget belongs to the user
		if (!budget.getUser().getId().equals(userId)) {
			throw new RuntimeException("Unauthorized access to budget");
		}

		Category category = null;
		if (budgetDTO.getCategoryId() != null) {
			category = categoryRepository.findById(budgetDTO.getCategoryId())
					.orElseThrow(() -> new RuntimeException("Category not found"));
		}

		budget.setName(budgetDTO.getName());
		budget.setDescription(budgetDTO.getDescription());
		budget.setAmount(budgetDTO.getAmount());
		budget.setStartDate(budgetDTO.getStartDate());
		budget.setEndDate(budgetDTO.getEndDate());
		budget.setCategory(category);

		Budget updatedBudget = budgetRepository.save(budget);
		BigDecimal spentAmount = calculateSpentAmount(updatedBudget);
		return convertToDTO(updatedBudget, spentAmount);
	}

	@Transactional
	public void deleteBudget(Long userId, Long budgetId) {
		Budget budget = budgetRepository.findById(budgetId).orElseThrow(() -> new RuntimeException("Budget not found"));

		// Check if the budget belongs to the user
		if (!budget.getUser().getId().equals(userId)) {
			throw new RuntimeException("Unauthorized access to budget");
		}

		budgetRepository.delete(budget);
	}

	private BigDecimal calculateSpentAmount(Budget budget) {
		// If there's no category associated with this budget, return zero
		if (budget.getCategory() == null) {
			return BigDecimal.ZERO;
		}

		// Find all transactions in the budget period for the specific category
		LocalDateTime startDateTime = budget.getStartDate().atStartOfDay();
		LocalDateTime endDateTime = budget.getEndDate().plusDays(1).atStartOfDay();

		List<Transaction> transactions = transactionRepository
				.findByUserAndTransactionDateBetween(budget.getUser(), startDateTime, endDateTime).stream()
				.filter(t -> t.getType() == TransactionType.Expense)
				.filter(t -> t.getCategory() != null && t.getCategory().getId().equals(budget.getCategory().getId()))
				.collect(Collectors.toList());

		return transactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BudgetDTO convertToDTO(Budget budget, BigDecimal spentAmount) {
		BudgetDTO dto = new BudgetDTO();
		dto.setId(budget.getId());
		dto.setName(budget.getName());
		dto.setDescription(budget.getDescription());
		dto.setAmount(budget.getAmount());
		dto.setStartDate(budget.getStartDate());
		dto.setEndDate(budget.getEndDate());

		if (budget.getCategory() != null) {
			dto.setCategoryId(budget.getCategory().getId());
			dto.setCategoryName(budget.getCategory().getName());
		}

		dto.setSpentAmount(spentAmount);
		dto.setRemainingAmount(budget.getAmount().subtract(spentAmount));

		return dto;
	}
}
