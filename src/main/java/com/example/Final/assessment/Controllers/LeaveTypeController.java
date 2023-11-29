package com.example.Final.assessment.Controllers;


import com.example.Final.assessment.Models.LeaveDTO;
import com.example.Final.assessment.Models.LeaveTypeDTO;
import com.example.Final.assessment.Services.BusinessService;
import com.example.Final.assessment.Services.LeaveTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leavetype")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;
    private final BusinessService businessService;

    public LeaveTypeController(LeaveTypeService leaveTypeService, BusinessService businessService) {
        this.leaveTypeService = leaveTypeService;
        this.businessService = businessService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LeaveTypeDTO>> getAllLeaveTypes() {
        List<LeaveTypeDTO> leavetypes = leaveTypeService.getAllLeaveTypes();
        return new ResponseEntity<>(leavetypes, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<LeaveTypeDTO> getLeaveTypeById(@PathVariable int id) {
        LeaveTypeDTO leaveType = leaveTypeService.getLeaveTypeById(id);
        if (leaveType != null) {
            return new ResponseEntity<>(leaveType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<LeaveTypeDTO> createLeaveType(@RequestBody LeaveTypeDTO leaveTypeDTO) {
        LeaveTypeDTO createdLeaveType = leaveTypeService.createLeaveType(leaveTypeDTO);
        return new ResponseEntity<>(createdLeaveType, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLeaveType(@PathVariable int id) {
        leaveTypeService.deleteLeaveType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("update/{leaveTypeId}")
    public ResponseEntity<String> updateLeaveType(@PathVariable int leaveTypeId, @RequestBody Map<String, Object> leaveTypeDTO) {
        leaveTypeService.updateLeaveType(leaveTypeId, leaveTypeDTO);
        String message = businessService.getMessage("{\"updated successfully\"}", null);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
