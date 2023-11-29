package com.example.Final.assessment.Controllers;

import com.example.Final.assessment.Models.ExpenseClaimDTO;
import com.example.Final.assessment.Models.ExpenseClaimEntryDTO;

import java.util.List;

public class ExpenseClaimRequest {

        private ExpenseClaimDTO expenseClaimDTO;
        private List<ExpenseClaimEntryDTO> entryDTOs;

        public ExpenseClaimDTO getExpenseClaimDTO() {
            return expenseClaimDTO;
        }

        public void setExpenseClaimDTO(ExpenseClaimDTO expenseClaimDTO) {
            this.expenseClaimDTO = expenseClaimDTO;
        }

        public List<ExpenseClaimEntryDTO> getEntryDTOs() {
            return entryDTOs;
        }

        public void setEntryDTOs(List<ExpenseClaimEntryDTO> entryDTOs) {
            this.entryDTOs = entryDTOs;
        }
    }
