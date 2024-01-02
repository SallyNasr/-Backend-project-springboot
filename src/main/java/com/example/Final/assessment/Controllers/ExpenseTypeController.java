package com.example.Final.assessment.Controllers;

import com.example.Final.assessment.Models.ExpenseClaimDTO;
import com.example.Final.assessment.Models.ExpenseTypeDTO;
import com.example.Final.assessment.Services.BusinessService;
import com.example.Final.assessment.Services.ExpenseTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/expensetypes")
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;
    private final BusinessService businessService;
    public ExpenseTypeController(ExpenseTypeService expenseTypeService, BusinessService businessService1) {
        this.expenseTypeService = expenseTypeService;
        this.businessService = businessService1;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExpenseTypeDTO>> getAllExpenseTypes() {
        List<ExpenseTypeDTO> expenseTypes = expenseTypeService.getAllExpenseTypes();
        return new ResponseEntity<>(expenseTypes, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ExpenseTypeDTO> getExpenseClaimById(@PathVariable int id) {
        ExpenseTypeDTO expenseType = expenseTypeService.getExpenseTypeById(id);
        if (expenseType != null) {
            return new ResponseEntity<>(expenseType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ExpenseTypeDTO> createExpenseType(@RequestBody ExpenseTypeDTO expenseTypeDTO) {
        ExpenseTypeDTO createdExpenseType = expenseTypeService.createExpenseType(expenseTypeDTO);
        return new ResponseEntity<>(createdExpenseType, HttpStatus.CREATED);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateExpenseType(@PathVariable int id, @RequestBody Map<String, Object> expenseTypeDTO) {
        expenseTypeService.updateExpenseType(id,expenseTypeDTO);
        String message = businessService.getMessage("{\"updated successfully\"}", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        expenseTypeService.deleteExpenseType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
