package com.example.expensetracker.mapper;

import com.example.expensetracker.dto.AdminExpenseResponseDto;
import com.example.expensetracker.model.Expense;

public class ExpenseMapper {

    public static AdminExpenseResponseDto toAdminDto(Expense expense) {
        AdminExpenseResponseDto dto = new AdminExpenseResponseDto();
        dto.setId(expense.getId());
        dto.setTitle(expense.getTitle());
        dto.setAmount(expense.getAmount());
        dto.setCategory(expense.getCategory());
        dto.setDate(expense.getDate());
        dto.setUserId(expense.getUser().getId());
        dto.setUserEmail(expense.getUser().getEmail());
        return dto;
    }
}
