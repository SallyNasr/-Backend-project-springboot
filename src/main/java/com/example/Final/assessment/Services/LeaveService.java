package com.example.Final.assessment.Services;

import com.example.Final.assessment.Mappers.EmployeeMapper;
import com.example.Final.assessment.Mappers.LeaveMapper;
import com.example.Final.assessment.Mappers.LeaveTypeMapper;
import com.example.Final.assessment.Models.*;
import com.example.Final.assessment.Models.LeaveDTO;
import com.example.Final.assessment.Repositories.EmployeeRepository;
import com.example.Final.assessment.Repositories.LeaveRepository;
import com.example.Final.assessment.Repositories.LeaveTypeRepository;
import com.example.Final.assessment.entities.DepartmentEntity;
import com.example.Final.assessment.entities.EmployeeEntity;
import com.example.Final.assessment.entities.LeaveEntity;
import com.example.Final.assessment.entities.LeavetypeEntity;
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
private final LeaveTypeMapper leaveTypeMapper;
private final EmployeeMapper employeeMapper;
private final LeaveTypeRepository leaveTypeRepository;
private final EmployeeRepository employeeRepository;

    public LeaveService(LeaveRepository leaveRepository, LeaveMapper leaveMapper, BusinessService businessService, LeaveTypeRepository leaveTypeRepository, EmployeeRepository employeeRepository, LeaveTypeMapper leaveTypeMapper, EmployeeMapper employeeMapper) {
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
        this.businessService = businessService;
        this.leaveTypeMapper = leaveTypeMapper;
        this.employeeMapper = employeeMapper;
        this.leaveTypeRepository = leaveTypeRepository;
        this.employeeRepository = employeeRepository;
    }

    //new way to get leaves down

//    public List<LeaveDTO> getAllLeaves() {
//        List<LeaveEntity> leaveEntities = leaveRepository.findAll();
//        return leaveEntities.stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }
//
//    private LeaveDTO mapToDTO(LeaveEntity leaveEntity) {
//        LeaveDTO leaveDTO = leaveMapper.leaveEntityToLeaveDTO(leaveEntity);
//
////         Fetch and set the name of the leave type
//        LeaveTypeDTO leaveTypeDTO = leaveDTO.getTypeOfLeaves();
////        LeavetypeEntity leaveType = leaveTypeMapper.leaveTypeDTOToLeaveTypeEntity(leaveTypeDTO);
//        leaveDTO.setTypeOfLeaves(leaveTypeDTO);
//
//        // Fetch and set the name of the employee
//        EmployeeDTO employee = leaveDTO.getEmployeeName();  // Assuming you have a getEmployee method
////        EmployeeDTO employeeDTO = employeeMapper.employeeEntityToEmployeeDTO(employee);  // Adjust the mapping method
//        leaveDTO.setEmployeeName(employee);
//
//        return leaveDTO;
//
//    }

    public List<LeaveDTO> getAllLeaves() {
        List<LeaveEntity> leaves = leaveRepository.findAll();
        return leaves.stream()
                .map(leave -> {
                    LeaveDTO leaveDTO = leaveMapper.leaveEntityToLeaveDTO(leave);

                    // Get TypeOfLeaves
                    LeavetypeEntity leaveTypeEntity = leaveTypeRepository.findById(leave.getLeaveType()).orElse(null);
                    if (leaveTypeEntity != null) {
                        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.leaveTypeEntityToLeaveTypeDTO(leaveTypeEntity);
                        leaveDTO.setTypeOfLeaves(leaveTypeDTO);
                    }

                    // Get employeeName
                    EmployeeEntity employeeEntity = employeeRepository.findById(leave.getEmployeeId()).orElse(null);
                    if (employeeEntity != null) {
                        EmployeeDTO employeeDTO = employeeMapper.employeeEntityToEmployeeDTO(employeeEntity);
                        leaveDTO.setEmployeeName(employeeDTO);
                    }

                    return leaveDTO;
                })
                .collect(Collectors.toList());
    }


    //new way to get leaves up

//    public List<LeaveDTO> getAllLeaves(){
//        List<LeaveEntity> leaves=leaveRepository.findAll();
//        return leaves.stream().map(leaveMapper::leaveEntityToLeaveDTO).collect(Collectors.toList());
//    }

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
            leaveRepository.save(leaveEntity);

        } catch (Exception e) {
            e.printStackTrace();

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
