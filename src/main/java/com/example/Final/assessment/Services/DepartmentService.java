package com.example.Final.assessment.Services;

import com.example.Final.assessment.Mappers.DepartmentMapper;
import com.example.Final.assessment.Models.DepartmentDTO;
import com.example.Final.assessment.Models.EmployeeDTO;
import com.example.Final.assessment.Repositories.DepartmentRepository;
import com.example.Final.assessment.entities.DepartmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final BusinessService businessService;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, BusinessService businessService) {
        this.departmentRepository = departmentRepository;
        this.businessService = businessService;
    }

    public List<DepartmentEntity> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public DepartmentDTO getDepartmentById(int departmentId) {
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).orElse(null);
        if (departmentEntity != null)
            return DepartmentMapper.INSTANCE.departmentEntityToDepartmentDTO(departmentEntity);
        return null;
    }

    public DepartmentEntity createDepartment(DepartmentEntity departmentEntity) {
        return departmentRepository.save(departmentEntity);
    }

    public void updateDepartment(int departmentId, Map<String, Object> departmentDTO) {
        DepartmentEntity entityToUpdate = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentService.ResourceNotFoundException("Department", "id", String.valueOf(departmentId)));

        businessService.updateEntity(departmentDTO, entityToUpdate, DepartmentEntity.class);

        departmentRepository.saveAndFlush(entityToUpdate);
    }

    public void deleteDepartment(int departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
            super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        }
    }
}
