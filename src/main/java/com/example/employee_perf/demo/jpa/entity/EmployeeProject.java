package com.example.employee_perf.demo.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "EMPLOYEE_PROJECT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProject {

    @Id
    @Column(name="EMPLOYEE_PROJECT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="EMPLOYEE_ID", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="PROJECT_ID", nullable = false)
    private Project project;
}
