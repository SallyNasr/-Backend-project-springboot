package com.example.Final.assessment.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
public class ExpenseClaimDTO {
    private int id;
   @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
    private String description;
    private Double total;
    private String status;
    private int employeeId;
//    private List<ExpenseClaimEntryDTO> entries=new ArrayList<>();
//   private Map<String, Double> totalAmountPerType;

}
