package com.example.Final.assessment.Mappers;

import com.example.Final.assessment.Models.LeaveTypeDTO;
import com.example.Final.assessment.entities.LeavetypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LeaveTypeMapper {
    LeaveTypeMapper INSTANCE = Mappers.getMapper(LeaveTypeMapper.class);

    LeaveTypeDTO leaveTypeEntityToLeaveTypeDTO(LeavetypeEntity leaveTypeEntity);
    @Mapping(target = "id", ignore = true)
    LeavetypeEntity leaveTypeDTOToLeaveTypeEntity(LeaveTypeDTO leaveTypeDTO);

}