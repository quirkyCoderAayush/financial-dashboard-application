package com.personal.financial_dashboard_backend.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class DashboardSummaryDTO {

	@NotNull(message = "Total income must not be null")
	@PositiveOrZero(message = "Total income must be zero or positive")
	private BigDecimal totalIncome;

	@NotNull(message = "Total expense must not be null")
	@PositiveOrZero(message = "Total expense must be zero or positive")
	private BigDecimal totalExpense;

	@NotNull(message = "Balance must not be null")
	private BigDecimal balance;

	@NotNull(message = "Expense by category list must not be null")
	private List<CategorySummaryDTO> expenseByCategory;

	@NotNull(message = "Income by category list must not be null")
	private List<CategorySummaryDTO> incomeByCategory;

	@NotNull(message = "Monthly expenses map must not be null")
	private Map<String, BigDecimal> monthlyExpenses;

	@NotNull(message = "Monthly incomes map must not be null")
	private Map<String, BigDecimal> monthlyIncomes;

	@NotNull(message = "Budgets list must not be null")
	private List<BudgetSummaryDTO> budgets;

	public DashboardSummaryDTO() {
	}

	public DashboardSummaryDTO(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal balance,
			List<CategorySummaryDTO> expenseByCategory, List<CategorySummaryDTO> incomeByCategory,
			Map<String, BigDecimal> monthlyExpenses, Map<String, BigDecimal> monthlyIncomes,
			List<BudgetSummaryDTO> budgets) {
		this.totalIncome = totalIncome;
		this.totalExpense = totalExpense;
		this.balance = balance;
		this.expenseByCategory = expenseByCategory;
		this.incomeByCategory = incomeByCategory;
		this.monthlyExpenses = monthlyExpenses;
		this.monthlyIncomes = monthlyIncomes;
		this.budgets = budgets;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public BigDecimal getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(BigDecimal totalExpense) {
		this.totalExpense = totalExpense;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<CategorySummaryDTO> getExpenseByCategory() {
		return expenseByCategory;
	}

	public void setExpenseByCategory(List<CategorySummaryDTO> expenseByCategory) {
		this.expenseByCategory = expenseByCategory;
	}

	public List<CategorySummaryDTO> getIncomeByCategory() {
		return incomeByCategory;
	}

	public void setIncomeByCategory(List<CategorySummaryDTO> incomeByCategory) {
		this.incomeByCategory = incomeByCategory;
	}

	public Map<String, BigDecimal> getMonthlyExpenses() {
		return monthlyExpenses;
	}

	public void setMonthlyExpenses(Map<String, BigDecimal> monthlyExpenses) {
		this.monthlyExpenses = monthlyExpenses;
	}

	public Map<String, BigDecimal> getMonthlyIncomes() {
		return monthlyIncomes;
	}

	public void setMonthlyIncomes(Map<String, BigDecimal> monthlyIncomes) {
		this.monthlyIncomes = monthlyIncomes;
	}

	public List<BudgetSummaryDTO> getBudgets() {
		return budgets;
	}

	public void setBudgets(List<BudgetSummaryDTO> budgets) {
		this.budgets = budgets;
	}

	// Inner static classes without Lombok

	public static class CategorySummaryDTO {

		@NotNull(message = "Category ID must not be null")
		private Long categoryId;

		@NotNull(message = "Category name must not be null")
		private String categoryName;

		private String color;

		@NotNull(message = "Amount must not be null")
		@PositiveOrZero(message = "Amount must be zero or positive")
		private BigDecimal amount;

		@PositiveOrZero(message = "Percentage must be zero or positive")
		private BigDecimal percentage;

		public CategorySummaryDTO() {
		}

		public CategorySummaryDTO(Long categoryId, String categoryName, String color, BigDecimal amount,
				BigDecimal percentage) {
			this.categoryId = categoryId;
			this.categoryName = categoryName;
			this.color = color;
			this.amount = amount;
			this.percentage = percentage;
		}

		public Long getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Long categoryId) {
			this.categoryId = categoryId;
		}

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public BigDecimal getPercentage() {
			return percentage;
		}

		public void setPercentage(BigDecimal percentage) {
			this.percentage = percentage;
		}
	}

	public static class BudgetSummaryDTO {

		@NotNull(message = "Budget ID must not be null")
		private Long budgetId;

		@NotNull(message = "Budget name must not be null")
		private String budgetName;

		private String categoryName;

		@NotNull(message = "Budget amount must not be null")
		@PositiveOrZero(message = "Budget amount must be zero or positive")
		private BigDecimal budgetAmount;

		@NotNull(message = "Spent amount must not be null")
		@PositiveOrZero(message = "Spent amount must be zero or positive")
		private BigDecimal spentAmount;

		@NotNull(message = "Remaining amount must not be null")
		@PositiveOrZero(message = "Remaining amount must be zero or positive")
		private BigDecimal remainingAmount;

		@PositiveOrZero(message = "Percentage must be zero or positive")
		private double percentage;

		public BudgetSummaryDTO() {
		}

		public BudgetSummaryDTO(Long budgetId, String budgetName, String categoryName, BigDecimal budgetAmount,
				BigDecimal spentAmount, BigDecimal remainingAmount, double percentage) {
			this.budgetId = budgetId;
			this.budgetName = budgetName;
			this.categoryName = categoryName;
			this.budgetAmount = budgetAmount;
			this.spentAmount = spentAmount;
			this.remainingAmount = remainingAmount;
			this.percentage = percentage;
		}

		public Long getBudgetId() {
			return budgetId;
		}

		public void setBudgetId(Long budgetId) {
			this.budgetId = budgetId;
		}

		public String getBudgetName() {
			return budgetName;
		}

		public void setBudgetName(String budgetName) {
			this.budgetName = budgetName;
		}

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public BigDecimal getBudgetAmount() {
			return budgetAmount;
		}

		public void setBudgetAmount(BigDecimal budgetAmount) {
			this.budgetAmount = budgetAmount;
		}

		public BigDecimal getSpentAmount() {
			return spentAmount;
		}

		public void setSpentAmount(BigDecimal spentAmount) {
			this.spentAmount = spentAmount;
		}

		public BigDecimal getRemainingAmount() {
			return remainingAmount;
		}

		public void setRemainingAmount(BigDecimal remainingAmount) {
			this.remainingAmount = remainingAmount;
		}

		public double getPercentage() {
			return percentage;
		}

		public void setPercentage(double percentage) {
			this.percentage = percentage;
		}
	}
}
