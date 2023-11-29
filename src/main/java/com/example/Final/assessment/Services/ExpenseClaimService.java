package com.example.Final.assessment.Services;

import com.example.Final.assessment.Mappers.EmployeeMapper;
import com.example.Final.assessment.Mappers.ExpenseClaimEntryMapper;
import com.example.Final.assessment.Mappers.ExpenseClaimMapper;
import com.example.Final.assessment.Models.EmployeeDTO;
import com.example.Final.assessment.Models.ExpenseClaimDTO;
import com.example.Final.assessment.Models.ExpenseClaimEntryDTO;
import com.example.Final.assessment.Models.ExpenseTypeDTO;
import com.example.Final.assessment.Repositories.ExpenseClaimEntryRepository;
import com.example.Final.assessment.Repositories.ExpenseClaimRepository;
import com.example.Final.assessment.Repositories.ExpenseTypeRepository;
import com.example.Final.assessment.entities.EmployeeEntity;
import com.example.Final.assessment.entities.ExpenseclaimEntity;
import com.example.Final.assessment.entities.ExpenseclaimentryEntity;
import com.example.Final.assessment.entities.ExpensetypeEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseClaimService {

    private final ExpenseClaimRepository expenseClaimRepository;
    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseClaimMapper expenseClaimMapper;
    private final BusinessService businessService;
    private final ExpenseClaimEntryRepository expenseClaimEntryRepository;
    private final ExpenseClaimEntryMapper expenseClaimEntryMapper;

    private final EmployeeMapper employeeMapper;

    public ExpenseClaimService(ExpenseClaimRepository expenseClaimRepository, ExpenseTypeRepository expenseTypeRepository, ExpenseClaimMapper expenseClaimMapper, BusinessService businessService, ExpenseClaimEntryRepository expenseClaimEntryRepository, ExpenseClaimEntryMapper expenseClaimEntryMapper, EmployeeMapper employeeMapper) {
        this.expenseClaimRepository = expenseClaimRepository;
        this.expenseTypeRepository = expenseTypeRepository;
        this.expenseClaimMapper = expenseClaimMapper;
        this.businessService = businessService;
        this.expenseClaimEntryRepository = expenseClaimEntryRepository;
        this.expenseClaimEntryMapper = expenseClaimEntryMapper;
        this.employeeMapper = employeeMapper;
    }

    public List<ExpenseClaimDTO> getAllExpenseClaims() {
        List<ExpenseclaimEntity> employees = expenseClaimRepository.findAll();
        return employees.stream()
                .map(expenseClaimMapper::ExpenseClaimEntityToExpenseClaimDTO)
                .collect(Collectors.toList());
    }

    public ExpenseClaimDTO getExpenseClaimById(int id) {
        ExpenseclaimEntity expenseclaim = expenseClaimRepository.findById(id).orElse(null);
        if (expenseclaim != null)
            return ExpenseClaimMapper.INSTANCE.ExpenseClaimEntityToExpenseClaimDTO(expenseclaim);
        return null;
    }

    public ExpenseClaimDTO createExpenseClaim(ExpenseClaimDTO expenseClaimDTO) {
        ExpenseclaimEntity expenseClaimEntity = expenseClaimMapper.ExpenseClaimDTOToExpenseClaimEntity(expenseClaimDTO);
        expenseClaimEntity = expenseClaimRepository.save(expenseClaimEntity);
        return expenseClaimMapper.ExpenseClaimEntityToExpenseClaimDTO(expenseClaimEntity);
    }

    public void updateExpenseClaim(int expenseClaimId, Map<String, Object> expenseClaimDTO) {
        ExpenseclaimEntity entityToUpdate = expenseClaimRepository.findById(expenseClaimId)
                .orElseThrow(() -> new ExpenseClaimService.ResourceNotFoundException("ExpenseClaim", "id", String.valueOf(expenseClaimId)));

        businessService.updateEntity(expenseClaimDTO, entityToUpdate, ExpenseclaimEntity.class);

        expenseClaimRepository.saveAndFlush(entityToUpdate);
    }

    public void deleteExpenseClaim(int id) {
        expenseClaimRepository.deleteById(id);
    }


    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
            super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        }
    }



    // Save an expense claim with entries
    public ExpenseClaimDTO saveExpenseClaimWithEntries(ExpenseClaimDTO expenseClaimDTO, List<ExpenseClaimEntryDTO> entryDTOs) {
        ExpenseclaimEntity expenseClaimEntity = expenseClaimMapper.ExpenseClaimDTOToExpenseClaimEntity(expenseClaimDTO);
        expenseClaimEntity.setStatus("success");
        expenseClaimEntity = expenseClaimRepository.save(expenseClaimEntity);

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Set expense claim date
        try {
            Date parsedDate = inputDateFormat.parse(String.valueOf(expenseClaimDTO.getDate()));
            expenseClaimEntity.setDate(new java.sql.Date(parsedDate.getTime()));
        } catch (ParseException e) {
            // Handle the exception as needed
        }

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ExpenseClaimEntryDTO entryDTO : entryDTOs) {

            // Fetch the ExpenseTypeEntity by ID
            int expenseTypeId = entryDTO.getExpenseType(); // Assuming getExpenseType returns the ID
            ExpensetypeEntity expenseType = expenseTypeRepository.findById(expenseTypeId)
                    .orElseThrow(() -> new EntityNotFoundException("Expense type not found with id: " + expenseTypeId));

            expenseTypeRepository.save(expenseType);

            entryDTO.setExpenseClaim(expenseClaimEntity.getId());
            totalAmount = totalAmount.add(BigDecimal.valueOf(entryDTO.getTotal())); // Convert double to BigDecimal

            ExpenseclaimentryEntity entryEntity = expenseClaimEntryMapper.ExpenseClaimEntryDTOToExpenseClaimEntryEntity(entryDTO);
            entryEntity.setExpenseClaim(expenseClaimEntity.getId());
            entryEntity.setExpenseType(expenseType.getId());
            expenseClaimEntryRepository.save(entryEntity);
        }

        expenseClaimEntity.setTotal(totalAmount.doubleValue());
        expenseClaimRepository.save(expenseClaimEntity);

        return expenseClaimMapper.ExpenseClaimEntityToExpenseClaimDTO(expenseClaimEntity);
    }

    //    public Map<String, Double> getTotalClaimsPerTypePerEmployee(int employeeId) {
//        List<Map<String, Double>> results = expenseClaimRepository.getTotalClaimsPerTypePerEmployee(employeeId);
//
//        Map<String, Double> totalClaimsPerType = new HashMap<>();
//
//        for (Map<String, Double> result : results) {
//            //  expenseType is an Integer in the database
//           Double expenseTypeValue = result.get("expenseType");
//           Integer expenseTypeId;
//            if (expenseTypeValue != null) {
//                expenseTypeId = (int) Math.round(expenseTypeValue); // Convert to Integer
//            }else {
//
////                throw new IllegalArgumentException("ExpenseType value is null");
//               expenseTypeId=3;
//            }
//            // You might want to fetch the ExpenseType name from the database based on expenseTypeId
//            String expenseTypeName = expenseTypeRepository.findById(employeeId)
//                    .map(ExpensetypeEntity::getName)
//                    .orElse("Unknown Expense Type");
//
//            Double totalAmount = result.get("totalClaims");
//
//            totalClaimsPerType.merge(expenseTypeName, totalAmount, Double::sum);
//        }
//        return totalClaimsPerType;
//    }

    //    show all the fields
     public List<Map<String, Double>> getTotalClaimsPerTypePerEmployee(int employeeId) {
        List<Map<String, Double>> results = expenseClaimRepository.getTotalClaimsPerTypePerEmployee(employeeId);
        return results;
    }



}