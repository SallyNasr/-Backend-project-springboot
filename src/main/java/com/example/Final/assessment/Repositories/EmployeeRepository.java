package com.example.Final.assessment.Repositories;

import com.example.Final.assessment.Models.EmployeeDTO;
import com.example.Final.assessment.entities.DepartmentEntity;
import com.example.Final.assessment.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository  extends JpaRepository<EmployeeEntity, Integer> {
    List<EmployeeEntity> findByDepartmentId(int departmentId);

}
