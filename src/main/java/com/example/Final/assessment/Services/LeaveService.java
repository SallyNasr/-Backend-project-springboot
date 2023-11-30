package com.example.Final.assessment.Services;

import com.example.Final.assessment.Mappers.LeaveMapper;
import com.example.Final.assessment.Models.LeaveDTO;
import com.example.Final.assessment.Repositories.LeaveRepository;
import com.example.Final.assessment.entities.LeaveEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public LeaveDTO getLeaveById(int id) {
        LeaveEntity leave = leaveRepository.findById(id).orElse(null);
        if (leave != null)
            return LeaveMapper.INSTANCE.leaveEntityToLeaveDTO(leave);
        return null;
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
//Submit leave request

    public LeaveDTO submitLeave(LeaveDTO leaveDTO) {

        LeaveEntity leaveEntity = leaveMapper.leaveDTOToLeaveEntity(leaveDTO);
        try {
            if (leaveDTO.getLeaveType() == 0) {
                throw new IllegalArgumentException("Leave type cannot be 0.");
            }
            leaveRepository.save(leaveEntity);
            leaveDTO.setError("No errors: added successfully" );

        } catch (IllegalArgumentException e) {
            // Set the error message in the LeaveDTO
           leaveDTO.setError("Leave submission failed: " + e.getMessage());
            return leaveDTO;
        } catch (Exception e) {
            e.printStackTrace();

            leaveDTO.setError("Failed to save leave entry."+ e.getMessage());
        }
      return leaveDTO;

    }

    public List<LeaveDTO> getLeavesByEmployeeIdAndFromDateAndToDate(int employeeId, Date fromDate, Date toDate) {
        List<LeaveEntity> leaveEntities = leaveRepository.getLeavesByEmployeeIdAndFromDateAndToDate(employeeId, fromDate, toDate);
        return leaveEntities.stream()
                .map(leaveMapper::leaveEntityToLeaveDTO)
                .collect(Collectors.toList());
    }

    public Page<LeaveDTO> getLeavesByLeaveTypeAndEmployeeId(Map<String, Object> parameters, int page, int size) {
        int leaveType = (int) parameters.get("leaveType");
        int employeeId = (int) parameters.get("employeeId");
        PageRequest pageable = PageRequest.of(page, size);
        Page<LeaveEntity> leavePage = leaveRepository.getLeavesByLeaveTypeAndEmployeeId(leaveType, employeeId, pageable);
        return leavePage.map(leaveMapper::leaveEntityToLeaveDTO);
    }

}
