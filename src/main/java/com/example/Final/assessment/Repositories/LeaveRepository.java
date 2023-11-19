package com.example.Final.assessment.Repositories;

import com.example.Final.assessment.entities.LeaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveEntity,Integer> {
//    List<LeaveEntity> findByLeaveType(String leaveType);

    //part 2
//    List<LeaveEntity> findByEmployeeIdAndDateRange(Long employeeId, Date fromDate, Date toDate);


}
