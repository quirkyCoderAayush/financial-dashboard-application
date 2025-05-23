package com.personal.financial_dashboard_backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.financial_dashboard_backend.dto.TransactionDTO;
import com.personal.financial_dashboard_backend.model.Category;
import com.personal.financial_dashboard_backend.model.Transaction;
import com.personal.financial_dashboard_backend.model.TransactionType;
import com.personal.financial_dashboard_backend.model.User;
import com.personal.financial_dashboard_backend.repository.CategoryRepository;
import com.personal.financial_dashboard_backend.repository.TransactionRepository;
import com.personal.financial_dashboard_backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;
	
	

	public TransactionService() {}

	public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository,
			UserRepository userRepository) {
		this.transactionRepository = transactionRepository;
		this.categoryRepository = categoryRepository;
		this.userRepository = userRepository;
	}

	public List<TransactionDTO> getAllTransactionsByUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		return transactionRepository.findByUserOrderByTransactionDateDesc(user).stream().map(this::convertToDTO)
				.collect(Collectors.toList());

	}
	
	public List<TransactionDTO> getTransactionsByType(Long userId, TransactionType type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> transactions = transactionRepository.findByUserAndType(user, type);
        return transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

	public List<TransactionDTO> getTransactionsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> transactions = transactionRepository.findByUserAndTransactionDateBetween(user, startDate, endDate);
        return transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

	public List<TransactionDTO> getTransactionsByCategory(Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> transactions = transactionRepository.findByUserAndCategoryId(user, categoryId);
        return transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransactionDTO createTransaction(Long userId, TransactionDTO transactionDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = null;
        if (transactionDTO.getCategoryId() != null) {
            category = categoryRepository.findById(transactionDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        Transaction transaction = new Transaction();
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setType(transactionDTO.getType());
        transaction.setCategory(category);
        transaction.setUser(user);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDTO(savedTransaction);
    }

    @Transactional
    public TransactionDTO updateTransaction(Long userId, Long transactionId, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Check if the transaction belongs to the user
        if (!transaction.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to transaction");
        }

        Category category = null;
        if (transactionDTO.getCategoryId() != null) {
            category = categoryRepository.findById(transactionDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setType(transactionDTO.getType());
        transaction.setCategory(category);

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return convertToDTO(updatedTransaction);
    }

    @Transactional
    public void deleteTransaction(Long userId, Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Check if the transaction belongs to the user
        if (!transaction.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to transaction");
        }

        transactionRepository.delete(transaction);
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setType(transaction.getType());

        if (transaction.getCategory() != null) {
            dto.setCategoryId(transaction.getCategory().getId());
            dto.setCategoryName(transaction.getCategory().getName());
            dto.setCategoryColor(transaction.getCategory().getColor());
        }

        return dto;
    }

}
