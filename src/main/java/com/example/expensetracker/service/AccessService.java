package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.Role;
import com.example.expensetracker.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class AccessService {

    public boolean isAdmin(User user){
        return user.getRoles().stream()
                .anyMatch(role -> role.getName() == Role.RoleName.ADMIN);
    }

    public void checkExpenseAccess(Expense expense, User currentUser){
        boolean isOwner = expense.getUser().getId().equals(currentUser.getId());
        if(!isOwner && !isAdmin(currentUser)){
            throw new AccessDeniedException("You don't have access to modify this expense");
        }
    }

}
