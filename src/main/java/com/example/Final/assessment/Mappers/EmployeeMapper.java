package com.example.Final.assessment.Mappers;

import com.example.Final.assessment.Models.EmployeeDTO;
import com.example.Final.assessment.entities.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    EmployeeDTO employeeEntityToEmployeeDTO(EmployeeEntity employeeEntity);
    @Mapping(target = "id", ignore = true)
    EmployeeEntity employeeDTOToEmployeeEntity(EmployeeDTO employeeDTO);


}