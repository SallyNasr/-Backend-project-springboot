package com.example.Final.assessment.Repositories;

import com.example.Final.assessment.Models.EmployeeDTO;
import com.example.Final.assessment.Models.LeaveDTO;
import com.example.Final.assessment.entities.LeaveEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveEntity,Integer> {

    List<LeaveEntity> getLeavesByEmployeeIdAndFromDateAndToDate(@Param("employeeId") int employeeId, @Param("fromDate") Date startDate, @Param("toDate") Date toDate);

    Page<LeaveEntity> getLeavesByLeaveTypeAndEmployeeId(int leaveType, int employeeId, PageRequest pageable);
}


