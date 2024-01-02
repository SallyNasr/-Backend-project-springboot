package com.example.Final.assessment.Repositories;

import com.example.Final.assessment.entities.LeavetypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeavetypeEntity,Integer> {

}
