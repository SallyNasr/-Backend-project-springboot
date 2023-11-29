package com.example.Final.assessment.Services;

import com.example.Final.assessment.Mappers.ExpenseClaimEntryMapper;
import com.example.Final.assessment.Models.ExpenseClaimEntryDTO;
import com.example.Final.assessment.Repositories.ExpenseClaimEntryRepository;
import com.example.Final.assessment.entities.ExpenseclaimentryEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseClaimEntryService {

    private final ExpenseClaimEntryRepository expenseClaimEntryRepository;
    private final ExpenseClaimEntryMapper expenseClaimEntryMapper;
    private final BusinessService businessService;
    public ExpenseClaimEntryService(ExpenseClaimEntryRepository expenseClaimEntryRepository, ExpenseClaimEntryMapper expenseClaimEntryMapper, BusinessService businessService) {
        this.expenseClaimEntryRepository = expenseClaimEntryRepository;
        this.expenseClaimEntryMapper = expenseClaimEntryMapper;
        this.businessService = businessService;
    }

    public List<ExpenseClaimEntryDTO> getAllExpenseClaimEntrys() {
        List<ExpenseclaimentryEntity> employees = expenseClaimEntryRepository.findAll();
        return employees.stream()
                .map(expenseClaimEntryMapper::ExpenseClaimEntryEntityToExpenseClaimEntryDTO)
                .collect(Collectors.toList());
    }

    public ExpenseClaimEntryDTO createExpenseClaimEntry(ExpenseClaimEntryDTO expenseClaimEntryDTO) {
        ExpenseclaimentryEntity expenseClaimEntryEntity=expenseClaimEntryMapper.ExpenseClaimEntryDTOToExpenseClaimEntryEntity(expenseClaimEntryDTO);
        expenseClaimEntryEntity= expenseClaimEntryRepository.save(expenseClaimEntryEntity);
        return expenseClaimEntryMapper.ExpenseClaimEntryEntityToExpenseClaimEntryDTO(expenseClaimEntryEntity);
    }

    public void updateExpenseClaimEntry(int expenseClaimEntryId, Map<String, Object> expenseClaimEntryDTO) {
        ExpenseclaimentryEntity entityToUpdate = expenseClaimEntryRepository.findById(expenseClaimEntryId)
                .orElseThrow(() -> new ExpenseClaimEntryService.ResourceNotFoundException("ExpenseClaimEntry", "id", String.valueOf(expenseClaimEntryId)));

        businessService.updateEntity(expenseClaimEntryDTO, entityToUpdate, ExpenseclaimentryEntity.class);

        expenseClaimEntryRepository.saveAndFlush(entityToUpdate);
    }

    public void deleteExpenseClaimEntry(int id) {
        expenseClaimEntryRepository.deleteById(id);
    }
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
            super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        }
    }




}

