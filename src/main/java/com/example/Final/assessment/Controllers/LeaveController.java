package com.example.Final.assessment.Controllers;

import com.example.Final.assessment.Models.LeaveDTO;
import com.example.Final.assessment.Services.BusinessService;
import com.example.Final.assessment.Services.LeaveService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
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



    @GetMapping("/find/{id}")
    public ResponseEntity<LeaveDTO> getLeaveById(@PathVariable int id) {
        LeaveDTO leave = leaveService.getLeaveById(id);
        if (leave != null) {
            return new ResponseEntity<>(leave, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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


    @PostMapping("/submit")
    public ResponseEntity<?> submitLeave(@RequestBody LeaveDTO leaveDTO) {
        if (leaveDTO.getLeaveType() != 0) {
            LeaveDTO responseDTO = leaveService.submitLeave(leaveDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Leave submission failed: Leave type cannot be null", HttpStatus.CREATED);

    }

    @PostMapping("/leaverange")
   public ResponseEntity<List<LeaveDTO>> getLeavesByEmployeeIdAndStartDateAndEndDate(
            @RequestParam int employeeId,
            @RequestParam Date fromDate,
            @RequestParam Date toDate) {
        List<LeaveDTO> leaves=leaveService.getLeavesByEmployeeIdAndFromDateAndToDate(employeeId, fromDate, toDate);
        return new ResponseEntity<>(leaves,HttpStatus.OK);
}

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
        return ResponseEntity.ok(leavePage);
    }


}




