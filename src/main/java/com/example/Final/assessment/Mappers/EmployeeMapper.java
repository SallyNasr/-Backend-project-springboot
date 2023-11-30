package com.example.Final.assessment.Mappers;

import com.example.Final.assessment.Models.DepartmentDTO;
import com.example.Final.assessment.Models.EmployeeDTO;
import com.example.Final.assessment.entities.DepartmentEntity;
import com.example.Final.assessment.entities.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    EmployeeDTO employeeEntityToEmployeeDTO(EmployeeEntity employeeEntity);
    @Mapping(target = "id", ignore = true)
    EmployeeEntity employeeDTOToEmployeeEntity(EmployeeDTO employeeDTO);

    List<EmployeeDTO> employeeEntitiesToEmployeeDTOs(List<EmployeeEntity> employeeEntities);

}