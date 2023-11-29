package com.example.Final.assessment.Models;

import com.example.Final.assessment.entities.DepartmentEntity;
import lombok.Data;

import java.util.List;


@Data
public class EmployeeDTO {
    private int id;
    private String address;
    private int departmentId;
    private String email;
    private String name;
//    private List <DepartmentDTO> department;
}