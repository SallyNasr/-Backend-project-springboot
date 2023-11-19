package com.example.Final.assessment.Controllers;

import com.example.Final.assessment.Services.DepartmentService;
import com.example.Final.assessment.entities.DepartmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Example endpoint: Get all departments
    @GetMapping
    public ResponseEntity<List<DepartmentEntity>> getAllDepartments() {
        List<DepartmentEntity> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<DepartmentEntity> createDepartment(@RequestBody DepartmentEntity departmentEntity) {
        DepartmentEntity createdDepartment = departmentService.createDepartment(departmentEntity);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

}
