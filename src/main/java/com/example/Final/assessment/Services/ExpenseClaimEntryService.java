package com.example.Final.assessment.Services;

import com.example.Final.assessment.Mappers.ExpenseClaimEntryMapper;
import com.example.Final.assessment.Mappers.ExpenseClaimMapper;
import com.example.Final.assessment.Models.ExpenseClaimDTO;
import com.example.Final.assessment.Models.ExpenseClaimEntryDTO;
import com.example.Final.assessment.Models.ExpenseTypeDTO;
import com.example.Final.assessment.Repositories.ExpenseClaimEntryRepository;
import com.example.Final.assessment.entities.ExpenseclaimEntity;
import com.example.Final.assessment.entities.ExpenseclaimentryEntity;
import com.example.Final.assessment.entities.ExpensetypeEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

//    public List<ExpenseClaimEntryDTO> getAllExpenseClaimEntries() {
//        List<ExpenseclaimentryEntity> employees = expenseClaimEntryRepository.findAll();
//        return employees.stream()
//                .map(expenseClaimEntryMapper::ExpenseClaimEntryEntityToExpenseClaimEntryDTO)
//                .collect(Collectors.toList());
//    }

    public List<ExpenseClaimEntryDTO> getAllExpenseClaimEntries() {
        List<ExpenseclaimentryEntity> expenseClaimEntries = expenseClaimEntryRepository.findAll();
        return expenseClaimEntries.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ExpenseClaimEntryDTO mapToDTO(ExpenseclaimentryEntity entity) {
        ExpenseClaimEntryDTO expenseClaimEntryDTO = expenseClaimEntryMapper.ExpenseClaimEntryEntityToExpenseClaimEntryDTO(entity);

        // Populate names
//        ExpenseClaimDTO expenseClaimDTO = mapToExpenseClaimDTO(entity.getExpenseClaim());
//        if (expenseClaimDTO != null) {
//            dto.setExpenseClaims(expenseClaimDTO);
//        }
//
//        ExpenseTypeDTO expenseTypeDTO = mapToExpenseTypeDTO(entity.getExpenseType());
//        if (expenseTypeDTO != null) {
//            dto.setExpenseTypes(expenseTypeDTO);
//        }
//
        return expenseClaimEntryDTO;
    }

    private ExpenseClaimDTO mapToExpenseClaimDTO(ExpenseclaimEntity entity) {
        if (entity == null) {
            return null;
        }

        ExpenseClaimDTO dto = new ExpenseClaimDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setDescription(entity.getDescription());
        dto.setTotal(entity.getTotal());
        dto.setStatus(entity.getStatus());
        dto.setEmployeeId(entity.getEmployeeId());
        // Map other fields if needed
        return dto;
    }

    private ExpenseTypeDTO mapToExpenseTypeDTO(ExpensetypeEntity entity) {
        if (entity == null) {
            return null;
        }

        ExpenseTypeDTO dto = new ExpenseTypeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        // Map other fields if needed
        return dto;
    }





    public ExpenseClaimEntryDTO getExpenseClaimEntryById(int id) {
        ExpenseclaimentryEntity expenseclaimentry = expenseClaimEntryRepository.findById(id).orElse(null);
        if (expenseclaimentry != null)
            return ExpenseClaimEntryMapper.INSTANCE.ExpenseClaimEntryEntityToExpenseClaimEntryDTO(expenseclaimentry);
        return null;
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

