package com.example.Final.assessment.Controllers;

import com.example.Final.assessment.Models.EmployeeDTO;
import com.example.Final.assessment.Services.BusinessService;
import com.example.Final.assessment.Services.EmployeeService;
import com.example.Final.assessment.entities.EmployeeEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    private final BusinessService businessService;

    public EmployeeController(EmployeeService employeeService, BusinessService businessService) {
        this.employeeService = employeeService;
        this.businessService = businessService;

    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable int id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byDepartment/{departmentId}")
    public ResponseEntity<List<EmployeeEntity>> getEmployeesByDepartment(@PathVariable int departmentId) {
        List<EmployeeEntity> employees = employeeService.getEmployeesByDepartment(departmentId);
        return new ResponseEntity<>(employees, HttpStatus.OK);

    }
    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable int employeeId, @RequestBody Map<String, Object> employeeDTO) {
        employeeService.updateEmployee(employeeId, employeeDTO);
        String message = businessService.getMessage("{\"updated successfully\"}", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PatchMapping("/assign/{employeeId}/{departmentId}")
    public ResponseEntity<EmployeeEntity> assignEmployeeToDepartment(@PathVariable int employeeId, @PathVariable int departmentId) {
        EmployeeEntity assignedEmployee = employeeService.assignEmployeeToDepartment(employeeId, departmentId);
        if (assignedEmployee != null) {
            return new ResponseEntity<>(assignedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
