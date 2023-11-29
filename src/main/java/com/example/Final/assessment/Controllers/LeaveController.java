package com.example.Final.assessment.Controllers;

import com.example.Final.assessment.Mappers.LeaveMapper;
import com.example.Final.assessment.Models.EmployeeDTO;
import com.example.Final.assessment.Models.LeaveDTO;
import com.example.Final.assessment.Repositories.LeaveRepository;
import com.example.Final.assessment.Services.BusinessService;
import com.example.Final.assessment.Services.EmployeeService;
import com.example.Final.assessment.Services.LeaveService;
import com.example.Final.assessment.entities.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    private final LeaveService leaveService;
    private final BusinessService businessService;
    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final EmployeeService employeeService;
    public LeaveController(LeaveService leaveService, BusinessService businessService, LeaveRepository leaveRepository, LeaveMapper leaveMapper, EmployeeService employeeService) {
        this.leaveService = leaveService;
        this.businessService = businessService;
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LeaveDTO>> getAllLeaves() {
        List<LeaveDTO> leaves = leaveService.getAllLeaves();
        return new ResponseEntity<>(leaves, HttpStatus.OK);
    }

//    @PostMapping("/add")
//    public ResponseEntity<LeaveDTO> createLeave(@RequestBody LeaveDTO leaveDTO) {
//        LeaveDTO createdLeave = leaveService.createLeave(leaveDTO);
//        return new ResponseEntity<>(createdLeave, HttpStatus.CREATED);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable int id) {
        leaveService.deleteLeave(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<String> updateLeave(@PathVariable int leaveId, @RequestBody Map<String, Object> leaveDTO) {
        leaveService.updateLeave(leaveId, leaveDTO);
        String message = businessService.getMessage("{\"updated successfully\"}", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//done jarabta
    @PostMapping("/submit")
    public ResponseEntity<LeaveDTO> submitLeave(@RequestBody LeaveDTO leaveDTO) {
       LeaveDTO responseDTO = leaveService.submitLeave(leaveDTO);
       return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

//done jarabta
    @PostMapping("/leaverange")
   public ResponseEntity<List<LeaveDTO>> getLeavesByEmployeeIdAndStartDateAndEndDate(
            @RequestParam int employeeId,
            @RequestParam Date fromDate,
            @RequestParam Date toDate) {
        List<LeaveDTO> leaves=leaveService.getLeavesByEmployeeIdAndFromDateAndToDate(employeeId, fromDate, toDate);
        return new ResponseEntity<>(leaves,HttpStatus.OK);
}
//zobtet jarabta
    @GetMapping("/byTypeAndEmployee")
    public ResponseEntity<Page<LeaveDTO>> getLeavesByLeaveTypeAndEmployeeId(
            @RequestParam int leaveType,
            @RequestParam int employeeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("leaveType", leaveType);
        parameters.put("employeeId", employeeId);
        Page<LeaveDTO> leavePage = leaveService.getLeavesByLeaveTypeAndEmployeeId( parameters, page, size);
        return ResponseEntity.ok(leavePage);}
}




