package com.example.Final.assessment.Controllers;

import com.example.Final.assessment.Models.LeaveDTO;
import com.example.Final.assessment.Services.BusinessService;
import com.example.Final.assessment.Services.LeaveService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    private final LeaveService leaveService;
    private final BusinessService businessService;

    public LeaveController(LeaveService leaveService, BusinessService businessService) {
        this.leaveService = leaveService;
        this.businessService = businessService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LeaveDTO>> getAllLeaves() {
        List<LeaveDTO> leaves = leaveService.getAllLeaves();
        return new ResponseEntity<>(leaves, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<LeaveDTO> createLeave(@RequestBody LeaveDTO leaveDTO) {
        LeaveDTO createdLeave = leaveService.createLeave(leaveDTO);
        return new ResponseEntity<>(createdLeave, HttpStatus.CREATED);
    }

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
    //
//    @GetMapping("/{employeeId}/leaves")
//    public ResponseEntity<List<LeaveDTO>> getLeavesForEmployeeInDateRange(
//            @PathVariable Long employeeId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
//
//        List<LeaveDTO> leaves = leaveService.getLeavesForEmployeeInDateRange(employeeId, fromDate, toDate);
//
//        return new ResponseEntity<>(leaves, HttpStatus.OK);
//    }

}
