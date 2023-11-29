package com.example.Final.assessment.Controllers;

import com.example.Final.assessment.Models.DepartmentDTO;
import com.example.Final.assessment.Models.EmployeeDTO;
import com.example.Final.assessment.Services.BusinessService;
import com.example.Final.assessment.Services.DepartmentService;
import com.example.Final.assessment.entities.DepartmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final BusinessService businessService;
    @Autowired
    public DepartmentController(DepartmentService departmentService, BusinessService businessService) {
        this.departmentService = departmentService;
        this.businessService = businessService;
    }

    @GetMapping("all")
    public ResponseEntity<List<DepartmentEntity>> getAllDepartments() {
        List<DepartmentEntity> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<DepartmentDTO> getEmployeeById(@PathVariable int id) {
       DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
        if (departmentDTO != null) {
            return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<DepartmentEntity> createDepartment(@RequestBody DepartmentEntity departmentEntity) {
        DepartmentEntity createdDepartment = departmentService.createDepartment(departmentEntity);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateDepartment(@PathVariable int id, @RequestBody Map<String, Object> departmentDTO) {
        departmentService.updateDepartment(id, departmentDTO);
        String message = businessService.getMessage("{\"updated successfully\"}", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable int id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
