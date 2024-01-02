package com.example.Final.assessment.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class ExpenseClaimRequestDTO {
    private ExpenseClaimDTO expenseClaimDTO;
    private List<EntryDTO> entryDTOs;

}
