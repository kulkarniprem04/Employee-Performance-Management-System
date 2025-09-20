package com.example.employee_perf.demo.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EmployeeFilterDto {
    private List<String> departmentNames;
    private Date reviewDate;
    private  List<String> projectNames;
}
