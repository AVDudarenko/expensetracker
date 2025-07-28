package com.example.expensetracker.controller;

import com.example.expensetracker.dto.AdminExpenseResponseDto;
import com.example.expensetracker.dto.ExpenseRequestDto;
import com.example.expensetracker.dto.ExpenseResponseDto;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense createExpense(@Valid @RequestBody ExpenseRequestDto requestDto, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        return expenseService.createExpense(requestDto, userEmail);
    }

    @GetMapping("/mine")
    public List<ExpenseResponseDto> getMyExpenses(Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        return expenseService.getAllExpensesForUser(userEmail);
    }

    @GetMapping("/admin")
    public Page<AdminExpenseResponseDto> getAllExpensesForAdmin(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            Pageable pageable
    ) {
        return expenseService.getAllExpensesForAdmin(category, fromDate, toDate, pageable);
    }
}
