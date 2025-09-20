package com.example.employee_perf.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private String name;
    private Date startDate;
    private Date endDate;
    private String DepartmentName;
}
