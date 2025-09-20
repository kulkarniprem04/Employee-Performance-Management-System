package com.example.employee_perf.demo.jpa.repository;

import com.example.employee_perf.demo.jpa.entity.Employee;
import com.example.employee_perf.demo.jpa.entity.EmployeeProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long>, JpaSpecificationExecutor<EmployeeProject> {

    List<EmployeeProject> findEmployeeProjectByEmployee(Employee employee);
}
