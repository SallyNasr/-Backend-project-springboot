package com.example.Final.assessment.Controllers;

import com.example.Final.assessment.Mappers.ExpenseClaimEntryMapper;
import com.example.Final.assessment.Models.ExpenseClaimDTO;
import com.example.Final.assessment.Models.ExpenseClaimEntryDTO;
import com.example.Final.assessment.Models.ExpenseClaimRequestDTO;
import com.example.Final.assessment.Models.ExpensePerTypePerEmployeeDTO;
import com.example.Final.assessment.Repositories.ExpenseClaimEntryRepository;
import com.example.Final.assessment.Services.BusinessService;
import com.example.Final.assessment.Services.ExpenseClaimService;
import com.example.Final.assessment.entities.ExpenseclaimentryEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/expenseclaims")
public class ExpenseClaimController {
    private final ExpenseClaimService expenseClaimService;
    private final BusinessService businessService;
    private final ExpenseClaimEntryRepository expenseClaimEntryRepository;
    private final ExpenseClaimEntryMapper expenseClaimEntryMapper;

    public ExpenseClaimController(ExpenseClaimService expenseClaimService, BusinessService businessService1, ExpenseClaimEntryRepository expenseClaimEntryRepository, ExpenseClaimEntryMapper expenseClaimEntryMapper) {
        this.expenseClaimService = expenseClaimService;
        this.businessService = businessService1;
        this.expenseClaimEntryRepository = expenseClaimEntryRepository;
        this.expenseClaimEntryMapper = expenseClaimEntryMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExpenseClaimDTO>> getAllExpenseClaims() {
        List<ExpenseClaimDTO> expenseClaims = expenseClaimService.getAllExpenseClaims();
        return new ResponseEntity<>(expenseClaims, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ExpenseClaimDTO> getExpenseClaimById(@PathVariable int id) {
        ExpenseClaimDTO expenseClaim = expenseClaimService.getExpenseClaimById(id);
        if (expenseClaim != null) {
            return new ResponseEntity<>(expenseClaim, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ExpenseClaimDTO> createExpenseClaim(@RequestBody ExpenseClaimDTO expenseClaimDTO) {
        ExpenseClaimDTO createdExpenseClaim = expenseClaimService.createExpenseClaim(expenseClaimDTO);
        return new ResponseEntity<>(createdExpenseClaim, HttpStatus.CREATED);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateExpenseClaim(@PathVariable int id, @RequestBody Map<String, Object> expenseClaimDTO) {
        expenseClaimService.updateExpenseClaim(id,expenseClaimDTO);
        String message = businessService.getMessage("{\"updated successfully\"}", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteExpenseClaim(@PathVariable int id) {
        expenseClaimService.deleteExpenseClaim(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/submitentries")
    public ResponseEntity<ExpenseClaimDTO> saveExpenseClaimWithEntries(
            @RequestBody ExpenseClaimRequest request){
        return ResponseEntity.ok(expenseClaimService.saveExpenseClaimWithEntries(request.getExpenseClaimDTO(), request.getEntryDTOs()));
    }


//    @GetMapping("/totalclaims/{employeeId}")
//    public ResponseEntity<Map<String, Double>> getTotalClaimsPerTypePerEmployee(@PathVariable("employeeId") int employeeId) {
//        Map<String, Double> result = expenseClaimService.getTotalClaimsPerTypePerEmployee(employeeId);
//        return ResponseEntity.ok(result);
//    }

    //second method show all the fields
    @GetMapping("/totalclaims/{employeeId}")
//    public List<Map<String, Double>> getTotalClaimsPerTypePerEmployee(@PathVariable int employeeId) {
//        return expenseClaimService.getTotalClaimsPerTypePerEmployee(employeeId);
//    }

    //new way:for frontend
    public ResponseEntity<List<ExpensePerTypePerEmployeeDTO>> getTotalClaimsPerTypePerEmployee(
            @PathVariable int employeeId) {
        List<ExpensePerTypePerEmployeeDTO> result = expenseClaimService.getTotalClaimsPerTypePerEmployee(employeeId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<ExpenseClaimDTO> getDetails(@PathVariable int id) {
        ExpenseClaimDTO result = expenseClaimService.getDetails(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}

