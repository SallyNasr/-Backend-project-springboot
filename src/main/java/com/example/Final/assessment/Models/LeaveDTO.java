package com.example.Final.assessment.Models;

import lombok.Data;

import java.sql.Date;

@Data
public class LeaveDTO {
    private int id;
    private int leaveType;
    private Date from;
    private Date to;
    private int numberOfDays;
    private String note;
}