package com.example.Final.assessment.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class ExpensePerTypePerEmployeeDTO {
    private String expenseType;
    private Double totalAmount;

    public ExpensePerTypePerEmployeeDTO(String expenseType, double totalAmount) {
        this.expenseType = expenseType;
        this.totalAmount = totalAmount;
    }
}
