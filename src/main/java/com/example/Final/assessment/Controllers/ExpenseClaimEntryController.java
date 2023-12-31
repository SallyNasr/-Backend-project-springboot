package com.example.Final.assessment.Controllers;

import com.example.Final.assessment.Models.ExpenseClaimDTO;
import com.example.Final.assessment.Models.ExpenseClaimEntryDTO;
import com.example.Final.assessment.Services.BusinessService;
import com.example.Final.assessment.Services.ExpenseClaimEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/expenseclaimsentry")
public class ExpenseClaimEntryController {

    private final ExpenseClaimEntryService expenseClaimEntryService;
    private final BusinessService businessService;
    public ExpenseClaimEntryController(ExpenseClaimEntryService expenseClaimEntryService, BusinessService businessService1) {
        this.expenseClaimEntryService = expenseClaimEntryService;
        this.businessService = businessService1;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExpenseClaimEntryDTO>> getAllExpenseClaimEntrys() {
        List<ExpenseClaimEntryDTO> expenseClaimEntries = expenseClaimEntryService.getAllExpenseClaimEntries();
        return new ResponseEntity<>(expenseClaimEntries, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ExpenseClaimEntryDTO> getExpenseClaimEntryById(@PathVariable int id) {
        ExpenseClaimEntryDTO expenseClaimEntry = expenseClaimEntryService.getExpenseClaimEntryById(id);
        if (expenseClaimEntry != null) {
            return new ResponseEntity<>(expenseClaimEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<ExpenseClaimEntryDTO> createExpenseClaimEntry(@RequestBody ExpenseClaimEntryDTO expenseClaimEntryDTO) {
        ExpenseClaimEntryDTO createdExpenseClaimEntry = expenseClaimEntryService.createExpenseClaimEntry(expenseClaimEntryDTO);
        return new ResponseEntity<>(createdExpenseClaimEntry, HttpStatus.CREATED);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateExpenseClaimEntry(@PathVariable int id, @RequestBody Map<String, Object> expenseClaimEntryDTO) {
        expenseClaimEntryService.updateExpenseClaimEntry(id,expenseClaimEntryDTO);
        String message = businessService.getMessage("{\"updated successfully\"}", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        expenseClaimEntryService.deleteExpenseClaimEntry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}

