package com.example.Final.assessment.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "leavetable", schema = "employees_database", catalog = "")
public class LeaveEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "leave_type")
    private int leaveType;
    @Basic
    @Column(name = "From_Date")
    private Date fromDate;
    @Basic
    @Column(name = "To_Date")
    private Date toDate;
    @Basic
    @Column(name = "number of days")
    private int numberOfDays;
    @Basic
    @Column(name = "note")
    private String note;
    @Basic
    @Column(name = "employee_id")
    private int employeeId;

}
