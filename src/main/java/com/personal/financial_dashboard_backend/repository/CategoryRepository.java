package com.personal.financial_dashboard_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.financial_dashboard_backend.model.Category;
import com.personal.financial_dashboard_backend.model.CategoryType;
import com.personal.financial_dashboard_backend.model.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findByUser(User user);

	List<Category> findByUserId(Long userId);

	List<Category> findByType(CategoryType type);

	List<Category> findByUserAndType(User user, CategoryType type);

}
