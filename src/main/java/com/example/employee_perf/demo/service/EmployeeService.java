package com.example.employee_perf.demo.service;

import com.example.employee_perf.demo.dto.EmployeeFilterDto;
import com.example.employee_perf.demo.dto.EmployeeFullDto;
import com.example.employee_perf.demo.dto.EmployeeShortDto;
import com.example.employee_perf.demo.dto.PerformanceReviewDto;
import com.example.employee_perf.demo.dto.ProjectDto;
import com.example.employee_perf.demo.jpa.Specification.EmployeeProjectSpecification;
import com.example.employee_perf.demo.jpa.Specification.EmployeeSpecifications;
import com.example.employee_perf.demo.jpa.Specification.PerformanceReviewSpecification;
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
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<EmployeeShortDto> getEmployees(EmployeeFilterDto employeeFilterDto) {
        Specification<Employee> spec = Specification.unrestricted();

        if(CollectionUtils.isNotEmpty(employeeFilterDto.getDepartmentNames())) {
            spec = spec.and(EmployeeSpecifications.belongsToDepartments(employeeFilterDto.getDepartmentNames()));
        }

        if(Objects.nonNull(employeeFilterDto.getReviewDate())) {
            spec = spec.and(EmployeeSpecifications.getEmployeeFromReviewDate(employeeFilterDto.getReviewDate()));
        }

        if(CollectionUtils.isNotEmpty(employeeFilterDto.getProjectNames())) {
            Specification<EmployeeProject> employeeProjectSpecification = EmployeeProjectSpecification.getProjectsFromNames(employeeFilterDto.getProjectNames());
            List<EmployeeProject> employeeProjects = employeeProjectRepository.findAll(employeeProjectSpecification);
            Set<Long> employeeIds = employeeProjects.stream().map(employeeProject -> employeeProject.getEmployee().getId()).collect(Collectors.toSet());
            spec = spec.and(EmployeeSpecifications.getEmployeeFromIds(employeeIds.stream().toList()));
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "name");
       List<Employee> employees =  employeeRepository.findAll(spec, sort);

       return getEmployeehortDTo(employees);
    }

    public ResponseEntity<EmployeeFullDto> getEmployeeWithId(Long employeeId) {
        Optional<Employee> emp = employeeRepository.findById(employeeId);
        EmployeeFullDto employeeFullDto = new EmployeeFullDto();
        if(emp.isPresent()) {
            Employee employee = emp.get();
            BeanUtils.copyProperties(employee, employeeFullDto);
            employeeFullDto.setDepartmentName(employee.getDepartment().getName());
            List<ProjectDto> projectDtos = getProjectstFromId(employee);
            employeeFullDto.setProjects(projectDtos);
           List<PerformanceReviewDto> performanceReviewDtos = getPerformanceReviewsFromEmployee(employee);
           employeeFullDto.setPerformanceReviews(performanceReviewDtos);
           return ResponseEntity.status(HttpStatus.OK).body(employeeFullDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    private List<ProjectDto> getProjectstFromId(Employee employee) {
        List<EmployeeProject> employeeProjects = employeeProjectRepository.findEmployeeProjectByEmployee(employee);
        List<ProjectDto> projectDtos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(employeeProjects)) {
            employeeProjects.forEach(employeeProject -> {
                ProjectDto projectDto = new ProjectDto();
                BeanUtils.copyProperties(employeeProject.getProject(), projectDto);
                projectDto.setDepartmentName(employeeProject.getProject().getDepartment().getName());
                projectDtos.add(projectDto);
            });
        }
        return projectDtos;
    }

    private List<PerformanceReviewDto> getPerformanceReviewsFromEmployee(Employee employee) {
        List<PerformanceReviewDto> performanceReviewDtos = new ArrayList<>();
        List<PerformanceReview> performanceReviews = employee.getPerformanceReviews();
        performanceReviews.sort(Comparator.comparing(PerformanceReview::getReviewDate).reversed());
        if(CollectionUtils.isNotEmpty(performanceReviews)) {
            performanceReviews.subList(0,3).forEach(performanceReview -> {
                PerformanceReviewDto performanceReviewDto = new PerformanceReviewDto();
                BeanUtils.copyProperties(performanceReview, performanceReviewDto);
                performanceReviewDtos.add(performanceReviewDto);
            });
        }
        return performanceReviewDtos;
    }

    private List<EmployeeShortDto> getEmployeehortDTo(List<Employee> employees) {
        List<EmployeeShortDto > employeeShortDtos = new ArrayList<>();
        employees.forEach((employee -> {
            EmployeeShortDto employeeShortDto = new EmployeeShortDto();
            BeanUtils.copyProperties(employee, employeeShortDto);
            employeeShortDto.setDepartmentName(employee.getDepartment().getName());
            if(Objects.nonNull(employee.getManager())) {
                employeeShortDto.setManagerName(employee.getManager().getName());
            }
            employeeShortDtos.add(employeeShortDto);
        }));

        return employeeShortDtos;
    }
}
