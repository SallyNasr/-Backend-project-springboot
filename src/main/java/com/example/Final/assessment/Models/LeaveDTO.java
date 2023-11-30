package com.example.Final.assessment.Models;

import lombok.*;

import java.sql.Date;


@Data
public class LeaveDTO {
    private int id;
    private int leaveType;
    private Date fromDate;
    private Date toDate;
    private int numberOfDays;
    private String note;
    private int employeeId;
    private String error; // New field for error message

}