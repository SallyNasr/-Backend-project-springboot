package com.example.Final.assessment.Services;

import com.example.Final.assessment.Mappers.DepartmentMapper;
import com.example.Final.assessment.Mappers.EmployeeMapper;
import com.example.Final.assessment.Models.DepartmentDTO;
import com.example.Final.assessment.Models.EmployeeDTO;
import com.example.Final.assessment.Repositories.DepartmentRepository;
import com.example.Final.assessment.Repositories.EmployeeRepository;
import com.example.Final.assessment.entities.DepartmentEntity;
import com.example.Final.assessment.entities.EmployeeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {


    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final BusinessService businessService;
    private final DepartmentMapper departmentMapper;
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, BusinessService businessService, DepartmentRepository departmentRepository, DepartmentMapper departmentMapper){
        this.employeeRepository=employeeRepository;
        this.employeeMapper=employeeMapper;
        this.businessService=businessService;
        this.departmentRepository=departmentRepository;
        this.departmentMapper = departmentMapper;
    }


//    public List<EmployeeDTO> getAllEmployees() {
//        List<EmployeeEntity> employees = employeeRepository.findAll();
//        return employees.stream()
//                .map(employeeMapper::employeeEntityToEmployeeDTO)
//                .collect(Collectors.toList());
//    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> {
                    EmployeeDTO employeeDTO = employeeMapper.employeeEntityToEmployeeDTO(employee);
                    DepartmentEntity departmentEntity = departmentRepository.findById(employee.getDepartmentId()).orElse(null);
                    if (departmentEntity != null) {
                        DepartmentDTO departmentDTO = departmentMapper.departmentEntityToDepartmentDTO(departmentEntity);
                        employeeDTO.setDepartment(departmentDTO);
                    }
                    return employeeDTO;
                })
                .collect(Collectors.toList());
    }


    public EmployeeDTO getEmployeeById(int employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElse(null);
        if (employeeEntity != null)
            return EmployeeMapper.INSTANCE.employeeEntityToEmployeeDTO(employeeEntity);
        return null;
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO){
        EmployeeEntity employeeEntity=employeeMapper.employeeDTOToEmployeeEntity(employeeDTO);
        employeeEntity=employeeRepository.save(employeeEntity);
        return employeeMapper.employeeEntityToEmployeeDTO(employeeEntity);
    }


    public EmployeeEntity assignEmployeeToDepartment(int employeeId, int departmentId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            employee.setDepartmentId(departmentId);
            return employeeRepository.save(employee);
        }
        return null;
    }

    public void updateEmployee(int employeeId, Map<String, Object> employeeDTO) {
        EmployeeEntity entityToUpdate = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", String.valueOf(employeeId)));

        businessService.updateEntity(employeeDTO, entityToUpdate, EmployeeEntity.class);

        employeeRepository.saveAndFlush(entityToUpdate);
    }

    public List<EmployeeDTO> getEmployeesByDepartment(int departmentId) {
        List<EmployeeEntity> employees = employeeRepository.findByDepartmentId(departmentId);

        return employeeMapper.employeeEntitiesToEmployeeDTOs(employees);
//                .stream()
//                .map(employeeDTO -> {
//                    DepartmentEntity departmentEntity = employeeRepository.findDepartmentByEmployeeId(employeeDTO.getId());
//                    DepartmentDTO departmentDTO = departmentMapper.departmentEntityToDepartmentDTO(departmentEntity);
//                    employeeDTO.setDepartment(departmentDTO);
//                    return employeeDTO;
//                })
//                .collect(Collectors.toList());
    }

    public void deleteEmployee(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
            super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        }
    }

}