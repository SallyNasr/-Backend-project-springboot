package com.example.Final.assessment.Repositories;

import com.example.Final.assessment.Models.ExpenseClaimDTO;
import com.example.Final.assessment.entities.ExpenseclaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ExpenseClaimRepository extends JpaRepository<ExpenseclaimEntity,Integer> {

    @Query("SELECT e.id AS employeeId, e.name AS employeeName, et.id AS expenseTypeId, et.name AS expenseTypeName, SUM(ec.total) AS totalClaims " +
            "FROM EmployeeEntity e " +
            "JOIN ExpenseclaimEntity ec ON e.id = ec.employeeId " +
            "JOIN ExpenseclaimentryEntity ece ON ec.id = ece.expenseClaim " +
            "JOIN ExpensetypeEntity et ON ece.expenseType = et.id " +
            "WHERE e.id = :employeeId " +
            "GROUP BY e.id, e.name, et.id, et.name")
    List<Map<String, Double>> getTotalClaimsPerTypePerEmployee(int employeeId);

}
