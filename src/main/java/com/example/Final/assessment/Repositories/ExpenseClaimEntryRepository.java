package com.example.Final.assessment.Repositories;

import com.example.Final.assessment.entities.ExpenseclaimentryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseClaimEntryRepository extends JpaRepository<ExpenseclaimentryEntity,Integer> {
}
