package com.example.Final.assessment.Services;

import com.example.Final.assessment.Mappers.LeaveMapper;
import com.example.Final.assessment.Models.LeaveDTO;
import com.example.Final.assessment.Repositories.LeaveRepository;
import com.example.Final.assessment.entities.LeaveEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final BusinessService businessService;

    public LeaveService(LeaveRepository leaveRepository, LeaveMapper leaveMapper, BusinessService businessService) {
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
        this.businessService = businessService;
    }


    public List<LeaveDTO> getAllLeaves(){
        List<LeaveEntity> leaves=leaveRepository.findAll();
        return leaves.stream().map(leaveMapper::leaveEntityToLeaveDTO).collect(Collectors.toList());
    }

    public LeaveDTO createLeave(LeaveDTO leaveDTO){
        LeaveEntity leaveEntity=leaveMapper.leaveDTOToLeaveEntity(leaveDTO);
        leaveEntity=leaveRepository.save(leaveEntity);
        return leaveMapper.leaveEntityToLeaveDTO(leaveEntity);
    }

    public void deleteLeave(int leaveId) {
        leaveRepository.deleteById(leaveId);
    }

    public void updateLeave(int leaveId, Map<String,Object> leaveDTO) {
        LeaveEntity entityToUpdate = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("leave", "id", String.valueOf(leaveId)));

        businessService.updateEntity(leaveDTO, entityToUpdate, LeaveEntity.class);

        leaveRepository.saveAndFlush(entityToUpdate);
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
            super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        }
    }


    //to retrieve the leaves of an employee for a specific range of dates (from, to)
//    public List<LeaveDTO> getLeavesForEmployeeInDateRange(Long employeeId, Date fromDate, Date toDate) {
//        List<LeaveEntity> leaves = leaveRepository.findByEmployeeIdAndDateRange(employeeId, fromDate, toDate);
//        return leaveMapper.leaveEntitiesToLeaveDTOs(leaves);
//    }

}
