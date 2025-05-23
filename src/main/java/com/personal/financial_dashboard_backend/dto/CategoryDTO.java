package com.personal.financial_dashboard_backend.dto;

import com.personal.financial_dashboard_backend.model.CategoryType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryDTO {

	private Long id;

	@NotEmpty
	@Size(min = 2, max = 50)
	private String name;

	@Size(max = 200)
	private String description;

	@NotNull
	private CategoryType type;

	@NotEmpty
	private String color;

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

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
