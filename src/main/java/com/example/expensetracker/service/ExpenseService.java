package com.example.expensetracker.service;

import com.example.expensetracker.dto.AdminExpenseResponseDto;
import com.example.expensetracker.dto.ExpenseRequestDto;
import com.example.expensetracker.dto.ExpenseResponseDto;
import com.example.expensetracker.mapper.ExpenseMapper;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public Expense createExpense(ExpenseRequestDto requestDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense(
                requestDto.getTitle(),
                requestDto.getAmount(),
                requestDto.getCategory(),
                requestDto.getDate() != null ? requestDto.getDate() : LocalDate.now(),
                user
        );

        return expenseRepository.save(expense);
    }

    public List<ExpenseResponseDto> getAllExpensesForUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return expenseRepository.findByUser(user)
                .stream()
                .map(expense -> new ExpenseResponseDto(
                        expense.getId(),
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getDate(),
                        expense.getCategory()
                ))
                .collect(Collectors.toList());
    }

    public List<AdminExpenseResponseDto> getAllExpensesForAdmin() {
        return expenseRepository.findAll()
                .stream()
                .map(ExpenseMapper::toAdminDto)
                .collect(Collectors.toList());
    }

    public Page<AdminExpenseResponseDto> getAllExpensesForAdmin(
            String category,
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable
    ) {
        Specification<Expense> spec = Specification.where(null);

        if (category != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category"), category));
        }

        if (fromDate != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), fromDate));
        }

        if (toDate != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), toDate));
        }

        return expenseRepository.findAll(spec, pageable)
                .map(ExpenseMapper::toAdminDto);
    }

}
