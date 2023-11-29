package com.example.Final.assessment.Mappers;

import com.example.Final.assessment.Models.ExpenseClaimDTO;
import com.example.Final.assessment.entities.ExpenseclaimEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ExpenseClaimMapper {
    ExpenseClaimMapper INSTANCE = Mappers.getMapper(ExpenseClaimMapper.class);
    ExpenseClaimDTO ExpenseClaimEntityToExpenseClaimDTO(ExpenseclaimEntity ExpenseClaimEntity);
    @Mapping(target = "id", ignore = true)
    ExpenseclaimEntity ExpenseClaimDTOToExpenseClaimEntity(ExpenseClaimDTO ExpenseClaimDTO);


}
