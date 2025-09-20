package com.example.employee_perf.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeShortDto {
    private Long id;

    private  String name;

    private String email;

    private Date dateOfJoining;

    private long salary;

    private String departmentName;

    private String managerName;
}
