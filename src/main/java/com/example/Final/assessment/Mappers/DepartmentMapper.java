package com.example.Final.assessment.Mappers;

import com.example.Final.assessment.Models.DepartmentDTO;
import com.example.Final.assessment.entities.DepartmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
    DepartmentDTO departmentEntityToDepartmentDTO(DepartmentEntity departmentEntity);//
    @Mapping(target = "id", ignore = true)
    DepartmentEntity departmentDTOToDepartmentEntity(DepartmentDTO departmentDTO);

}