package com.example.Final.assessment.Repositories;

import com.example.Final.assessment.entities.ExpenseclaimentryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseClaimEntryRepository extends JpaRepository<ExpenseclaimentryEntity,Integer> {
    @Query("SELECT COALESCE(SUM(e.total), 0) FROM ExpenseclaimentryEntity e " +
            "WHERE e.expenseType = :expenseTypeId AND e.expenseClaim IN " +
            "(SELECT ec.id FROM ExpenseclaimEntity ec WHERE ec.employeeId = :employeeId)")
    Double getTotalAmountByTypeAndEmployee(@Param("expenseTypeId") int expenseTypeId, @Param("employeeId") int employeeId);

}
