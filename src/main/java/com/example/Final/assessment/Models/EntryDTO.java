package com.example.Final.assessment.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class EntryDTO {
    private String date;
    private int expenseType;
    private String description;
    private double total;
}
