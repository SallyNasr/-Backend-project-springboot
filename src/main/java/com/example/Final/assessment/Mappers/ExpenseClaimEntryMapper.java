package com.example.Final.assessment.Mappers;

import com.example.Final.assessment.Models.ExpenseClaimEntryDTO;
import com.example.Final.assessment.entities.ExpenseclaimentryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseClaimEntryMapper {
    ExpenseClaimEntryMapper INSTANCE = Mappers.getMapper(ExpenseClaimEntryMapper.class);
    ExpenseClaimEntryDTO ExpenseClaimEntryEntityToExpenseClaimEntryDTO(ExpenseclaimentryEntity ExpenseClaimEntryEntity);
    @Mapping(target = "id", ignore = true)
    ExpenseclaimentryEntity ExpenseClaimEntryDTOToExpenseClaimEntryEntity(ExpenseClaimEntryDTO ExpenseClaimEntryDTO);

}
