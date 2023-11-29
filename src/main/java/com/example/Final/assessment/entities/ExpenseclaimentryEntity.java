package com.example.Final.assessment.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Setter
@Getter
@Entity
@Table(name = "expenseclaimentry", schema = "employees_database", catalog = "")
public class ExpenseclaimentryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;
    @Basic
    @Column(name = "expense_type")
    private int expenseType;
    @Basic
    @Column(name = "expense_claim")
    private int expenseClaim;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "total")
    private Double total;
}
