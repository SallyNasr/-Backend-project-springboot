package com.example.Final.assessment.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Date;

@Data
public class ExpenseClaimEntryDTO {
    private int id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
    private int expenseType;
    private int expenseClaim;
    private String description;
    private Double total;
    private ExpenseClaimDTO expenseClaims;
    private ExpenseTypeDTO expenseTypes;

}
