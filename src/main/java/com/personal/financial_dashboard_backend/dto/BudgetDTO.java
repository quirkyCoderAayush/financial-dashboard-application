package com.personal.financial_dashboard_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BudgetDTO {

	private Long id;

	@NotEmpty
	@Size(min = 2, max = 50)
	private String name;

	@Size(max = 200)
	private String description;

	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal amount;

	@NotNull
	private LocalDate startDate;

	@NotNull
	private LocalDate endDate;

	@NotNull
	private Long categoryId;

	@NotEmpty
	private String categoryName;

	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
	private BigDecimal spentAmount;

	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
	private BigDecimal remainingAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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

}
