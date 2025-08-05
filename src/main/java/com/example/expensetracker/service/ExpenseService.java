package com.example.expensetracker.service;

import com.example.expensetracker.dto.AdminExpenseResponseDto;
import com.example.expensetracker.dto.ExpenseRequestDto;
import com.example.expensetracker.dto.ExpenseResponseDto;
import com.example.expensetracker.exception.NotFoundException;
import com.example.expensetracker.mapper.ExpenseMapper;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.example.expensetracker.mapper.ExpenseMapper.toDto;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CurrentUserService currentUserService;
    private final AccessService accessService;

    public ExpenseService(ExpenseRepository expenseRepository,
                          CurrentUserService currentUserService,
                          AccessService accessService) {
        this.expenseRepository = expenseRepository;
        this.currentUserService = currentUserService;
        this.accessService = accessService;
    }

    // ------------- CREATE -------------

    public ExpenseResponseDto createExpense(ExpenseRequestDto requestDto) {
        User currentUser = currentUserService.getCurrentUser();

        Expense expense = new Expense();
        expense.setTitle(requestDto.getTitle());
        expense.setAmount(requestDto.getAmount());
        expense.setCategory(requestDto.getCategory());
        expense.setDate(requestDto.getDate());
        expense.setUser(currentUser);

        expenseRepository.save(expense);

        return toDto(expense);
    }

    // ------------- READ -------------

    public List<ExpenseResponseDto> getMyExpenses() {
        User currentUser = currentUserService.getCurrentUser();
        return expenseRepository.findByUser(currentUser).stream()
                .map(ExpenseMapper::toDto)
                .toList();
    }

    public List<AdminExpenseResponseDto> getAllExpensesForAdmin() {
        return expenseRepository.findAll().stream()
                .map(ExpenseMapper::toAdminDto)
                .toList();
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

    // ------------- UPDATE -------------

    @Transactional
    public ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto expenseRequestDto) {
        User currentUser = currentUserService.getCurrentUser();
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Expense not found"));

        accessService.checkExpenseAccess(expense, currentUser);

        expense.setTitle(expenseRequestDto.getTitle());
        expense.setAmount(expenseRequestDto.getAmount());
        expense.setCategory(expenseRequestDto.getCategory());
        expense.setDate(expenseRequestDto.getDate());

        expenseRepository.save(expense);

        return toDto(expense);
    }

    // ------------- DELETE -------------

    @Transactional
    public void deleteExpense(Long id) {
        User currentUser = currentUserService.getCurrentUser();
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Expense not found"));

        accessService.checkExpenseAccess(expense, currentUser);

        expenseRepository.delete(expense);
    }

}
