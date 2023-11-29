package com.example.Final.assessment.Services;

import com.example.Final.assessment.Mappers.ExpenseClaimMapper;
import com.example.Final.assessment.Mappers.ExpenseTypeMapper;
import com.example.Final.assessment.Models.ExpenseClaimDTO;
import com.example.Final.assessment.Models.ExpenseTypeDTO;
import com.example.Final.assessment.Repositories.ExpenseTypeRepository;
import com.example.Final.assessment.entities.ExpenseclaimEntity;
import com.example.Final.assessment.entities.ExpensetypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseTypeService {

    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseTypeMapper expenseTypeMapper;
    private final BusinessService businessService;
    public ExpenseTypeService(ExpenseTypeRepository expenseTypeRepository, ExpenseTypeMapper expenseTypeMapper, BusinessService businessService) {
        this.expenseTypeRepository = expenseTypeRepository;
        this.expenseTypeMapper = expenseTypeMapper;
        this.businessService = businessService;
    }

    public List<ExpenseTypeDTO> getAllExpenseTypes() {
        List<ExpensetypeEntity> employees = expenseTypeRepository.findAll();
        return employees.stream()
                .map(expenseTypeMapper::ExpenseTypeEntityToExpenseTypeDTO)
                .collect(Collectors.toList());
    }

    public ExpenseTypeDTO getExpenseTypeById(int id) {
        ExpensetypeEntity expenseType = expenseTypeRepository.findById(id).orElse(null);
        if (expenseType != null)
            return ExpenseTypeMapper.INSTANCE.ExpenseTypeEntityToExpenseTypeDTO(expenseType);
        return null;
    }

    public ExpenseTypeDTO createExpenseType(ExpenseTypeDTO expenseTypeDTO) {
        ExpensetypeEntity expenseTypeEntity=expenseTypeMapper.ExpenseTypeDTOToExpenseTypeEntity(expenseTypeDTO);
        expenseTypeEntity= expenseTypeRepository.save(expenseTypeEntity);
        return expenseTypeMapper.ExpenseTypeEntityToExpenseTypeDTO(expenseTypeEntity);
    }

    public void updateExpenseType(int expenseTypeId, Map<String, Object> expenseTypeDTO) {
        ExpensetypeEntity entityToUpdate = expenseTypeRepository.findById(expenseTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseType", "id", String.valueOf(expenseTypeId)));

        businessService.updateEntity(expenseTypeDTO, entityToUpdate, ExpensetypeEntity.class);

        expenseTypeRepository.saveAndFlush(entityToUpdate);
    }

    public void deleteExpenseType(int id) {
        expenseTypeRepository.deleteById(id);
    }
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
            super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        }
    }



}
