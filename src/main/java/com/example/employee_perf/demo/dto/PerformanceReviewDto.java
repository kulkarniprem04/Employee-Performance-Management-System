package com.example.employee_perf.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceReviewDto {

    private Date reviewDate;
    private Integer score;
    private String reviewComments;
}
