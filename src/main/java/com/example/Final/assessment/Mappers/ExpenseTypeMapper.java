package com.example.Final.assessment.Mappers;

import com.example.Final.assessment.Models.ExpenseTypeDTO;
import com.example.Final.assessment.entities.ExpensetypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseTypeMapper {
    ExpenseTypeMapper INSTANCE = Mappers.getMapper(ExpenseTypeMapper.class);
    ExpenseTypeDTO ExpenseTypeEntityToExpenseTypeDTO(ExpensetypeEntity ExpenseTypeEntity);
    @Mapping(target = "id", ignore = true)
    ExpensetypeEntity ExpenseTypeDTOToExpenseTypeEntity(ExpenseTypeDTO ExpenseTypeDTO);
}
