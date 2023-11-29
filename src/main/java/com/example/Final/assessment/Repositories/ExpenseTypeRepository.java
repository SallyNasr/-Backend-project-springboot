package com.example.Final.assessment.Repositories;

import com.example.Final.assessment.entities.ExpenseclaimEntity;
import com.example.Final.assessment.entities.ExpensetypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpensetypeEntity,Integer> {
}
