package com.example.expensetracker.controller;

import com.example.expensetracker.common.ApiResponse;
import com.example.expensetracker.dto.AdminExpenseResponseDto;
import com.example.expensetracker.dto.ExpenseRequestDto;
import com.example.expensetracker.dto.ExpenseResponseDto;
import com.example.expensetracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Operation(summary = "Create expense")
    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> createExpense(
            @Valid @RequestBody ExpenseRequestDto requestDto) {
        ExpenseResponseDto saved = expenseService.createExpense(requestDto);
        return ResponseEntity.status(201).body(ApiResponse.success("expense.created", saved));
    }

    @Operation(summary = "My expenses (with pagination & filters)")
    @GetMapping("/mine")
    public ResponseEntity<ApiResponse<Page<ExpenseResponseDto>>> getMyExpenses(
            @Parameter(description = "Category filter") @RequestParam(required = false) String category,
            @Parameter(description = "From date (YYYY-MM-DD)") @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @Parameter(description = "To date (YYYY-MM-DD)") @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @Parameter(description = "Pagination params") @PageableDefault(size = 20, sort = "date")
            Pageable pageable
    ) {
        Page<ExpenseResponseDto> page = expenseService.getMyExpenses(category, fromDate, toDate, pageable);
        return ResponseEntity.ok(ApiResponse.success("expense.list", page));
    }

    @Operation(summary = "Get expense by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> getExpenseById(@PathVariable Long id) {
        ExpenseResponseDto dto = expenseService.getExpenseById(id);
        return ResponseEntity.ok(ApiResponse.success("expense.item", dto));
    }

    @Operation(summary = "All expenses (ADMIN) with pagination & filters")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<AdminExpenseResponseDto>>> getAllExpensesForAdmin(
            @Parameter(description = "Category filter") @RequestParam(required = false) String category,
            @Parameter(description = "From date (YYYY-MM-DD)") @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @Parameter(description = "To date (YYYY-MM-DD)") @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @Parameter(description = "Pagination params") @PageableDefault(size = 20, sort = "date")
            Pageable pageable
    ) {
        Page<AdminExpenseResponseDto> page = expenseService.getAllExpensesForAdmin(category, fromDate, toDate, pageable);
        return ResponseEntity.ok(ApiResponse.success("expense.list.admin", page));
    }

    @Operation(summary = "Update expense")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponseDto>> updateExpense(
            @PathVariable Long id,
            @RequestBody @Valid ExpenseRequestDto requestDto) {
        ExpenseResponseDto updated = expenseService.updateExpense(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("expense.updated", updated));
    }

    @Operation(summary = "Delete expense")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
