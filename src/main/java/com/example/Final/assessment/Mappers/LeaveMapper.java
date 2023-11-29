package com.example.Final.assessment.Mappers;

import com.example.Final.assessment.Models.LeaveDTO;
import com.example.Final.assessment.entities.LeaveEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LeaveMapper {
    LeaveMapper INSTANCE = Mappers.getMapper(LeaveMapper.class);

    LeaveDTO leaveEntityToLeaveDTO(LeaveEntity leaveEntity);

    @Mapping(target = "id", ignore = true)
    LeaveEntity leaveDTOToLeaveEntity(LeaveDTO leaveDTO);

    //part 2
    List<LeaveDTO> leaveEntitiesToLeaveDTOs(List<LeaveEntity> leaves);
}
