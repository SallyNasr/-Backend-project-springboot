package com.example.Final.assessment.Services;

import com.example.Final.assessment.Mappers.LeaveTypeMapper;
import com.example.Final.assessment.Models.LeaveTypeDTO;
import com.example.Final.assessment.Repositories.LeaveTypeRepository;
import com.example.Final.assessment.entities.LeavetypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveTypeMapper leaveTypeMapper;
    private final BusinessService businessService;

    public LeaveTypeService(LeaveTypeRepository leaveTypeRepository, LeaveTypeMapper leaveTypeMapper, BusinessService businessService) {
        this.leaveTypeRepository = leaveTypeRepository;
        this.leaveTypeMapper = leaveTypeMapper;
        this.businessService = businessService;
    }

    public List<LeaveTypeDTO> getAllLeaveTypes(){
        List<LeavetypeEntity> leaves=leaveTypeRepository.findAll();
        return leaves.stream().map(leaveTypeMapper::leaveTypeEntityToLeaveTypeDTO).collect(Collectors.toList());
    }

    public LeaveTypeDTO getLeaveTypeById(int id) {
        LeavetypeEntity leaveType = leaveTypeRepository.findById(id).orElse(null);
        if (leaveType != null)
            return LeaveTypeMapper.INSTANCE.leaveTypeEntityToLeaveTypeDTO(leaveType);
        return null;
    }

    public LeaveTypeDTO createLeaveType(LeaveTypeDTO leaveTypeDTO){
        LeavetypeEntity leavetypeEntity=leaveTypeMapper.leaveTypeDTOToLeaveTypeEntity(leaveTypeDTO);
        leavetypeEntity=leaveTypeRepository.save(leavetypeEntity);
        return leaveTypeMapper.leaveTypeEntityToLeaveTypeDTO(leavetypeEntity);
    }

    public void deleteLeaveType(int leaveTypeId) {
        leaveTypeRepository.deleteById(leaveTypeId);
    }

    public void updateLeaveType(int leaveTypeId, Map<String,Object> leaveTypeDTO) {
        LeavetypeEntity entityToUpdate = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("leavetype", "id", String.valueOf(leaveTypeId)));

        businessService.updateEntity(leaveTypeDTO, entityToUpdate, LeavetypeEntity.class);

        leaveTypeRepository.saveAndFlush(entityToUpdate);
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
            super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        }
    }
}
