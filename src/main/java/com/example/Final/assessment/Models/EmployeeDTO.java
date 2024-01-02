package com.example.Final.assessment.Models;

import lombok.*;

import java.util.List;


@Data
public class EmployeeDTO {
    private int id;
    private String address;
    private int departmentId;
    private String email;
    private String name;
    private DepartmentDTO department;
}
