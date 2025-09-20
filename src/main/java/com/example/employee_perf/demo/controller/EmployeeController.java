package com.example.employee_perf.demo.controller;

import com.example.employee_perf.demo.dto.EmployeeFilterDto;
import com.example.employee_perf.demo.dto.EmployeeFullDto;
import com.example.employee_perf.demo.dto.EmployeeShortDto;
import com.example.employee_perf.demo.jpa.entity.Employee;
import com.example.employee_perf.demo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "employee/")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/getEmployeeWithFilters")
    public List<EmployeeShortDto> getEmployees(@RequestBody EmployeeFilterDto employeeFilterDto) {
        return employeeService.getEmployees(employeeFilterDto);
    }

    @GetMapping("/getEMployeeWithId")
    public ResponseEntity<EmployeeFullDto> getEmployeeWithId(@RequestParam(value = "id", required = true) Long employeeId) {
        return employeeService.getEmployeeWithId(employeeId);
    }
}
