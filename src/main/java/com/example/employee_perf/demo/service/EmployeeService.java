package com.example.employee_perf.demo.service;

import com.example.employee_perf.demo.jpa.entity.Department;
import com.example.employee_perf.demo.jpa.entity.Employee;
import com.example.employee_perf.demo.jpa.entity.EmployeeProject;
import com.example.employee_perf.demo.jpa.entity.PerformanceReview;
import com.example.employee_perf.demo.jpa.entity.Project;
import com.example.employee_perf.demo.jpa.repository.DepartmentRepository;
import com.example.employee_perf.demo.jpa.repository.EmployeeProjectRepository;
import com.example.employee_perf.demo.jpa.repository.EmployeeRepository;
import com.example.employee_perf.demo.jpa.repository.PerformanceReviewsRepository;
import com.example.employee_perf.demo.jpa.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmployeeService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeProjectRepository employeeProjectRepository;
    private final PerformanceReviewsRepository performanceReviewsRepository;

    public EmployeeService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, ProjectRepository projectRepository, EmployeeProjectRepository employeeProjectRepository, PerformanceReviewsRepository performanceReviewsRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.employeeProjectRepository = employeeProjectRepository;
        this.performanceReviewsRepository = performanceReviewsRepository;
    }

    public List<Employee> save() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Departments
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1L, "Engineering", 5000000L));
        departments.add(new Department(2L, "DevOps", 2500000L));
        departmentRepository.saveAll(departments);

        //Employees
        List<Employee> employees = new ArrayList<>();
        try {
            employees.add(new Employee(3L, "Prem", "kulkarniprem04@gmail.com",  simpleDateFormat.parse("2022-07-18"),800000L,departments.getFirst(), null));
            employees.add(new Employee(1L, "Preetam", "kulkarnipreetam04@gmail.com",  simpleDateFormat.parse("2022-07-18"),800000L,departments.getFirst(), employees.getFirst()));
            employees.add(new Employee(2L, "Prateek", "kulkarniprateek04@gmail.com",  simpleDateFormat.parse("2022-07-18"),800000L,departments.getFirst(), employees.getFirst()));
            employeeRepository.saveAll(employees);

            //Project
            List<Project> projects = new ArrayList<>();
            projects.add(new Project(1L, "Prisma", simpleDateFormat.parse("2020-06-01"), simpleDateFormat.parse("2026-06-01"), departments.getFirst()));
            projects.add(new Project(2L, "Spectra", simpleDateFormat.parse("2020-06-01"), simpleDateFormat.parse("2026-06-01"), departments.getFirst()));
            projectRepository.saveAll(projects);

            //Performance Review
            List<PerformanceReview> performanceReviews = new ArrayList<>();
            performanceReviews.add(new PerformanceReview(1L, employees.getFirst(), simpleDateFormat.parse("2025-06-01"), 80, "No COMMENTS"));
            performanceReviews.add(new PerformanceReview(2L, employees.get(1), simpleDateFormat.parse("2025-06-01"), 80, "No COMMENTS"));
            performanceReviewsRepository.saveAll(performanceReviews);

            //Employee_Project
            List<EmployeeProject> employeeProjects = new ArrayList<>();
            employeeProjects.add(new EmployeeProject(1L, employees.getFirst(), projects.getFirst()));
            employeeProjects.add(new EmployeeProject(2L, employees.get(1), projects.get(1)));
            employeeProjects.add(new EmployeeProject(3L, employees.getFirst(), projects.get(1)));
            employeeProjects.add(new EmployeeProject(4L, employees.get(1), projects.getFirst()));
            employeeProjectRepository.saveAll(employeeProjects);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }
}
