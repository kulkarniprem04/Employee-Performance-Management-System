package com.example.employee_perf.demo.jpa.Specification;

import com.example.employee_perf.demo.jpa.entity.EmployeeProject;
import com.example.employee_perf.demo.jpa.entity.Project;
import jakarta.persistence.criteria.Join;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProjectSpecification {

    public static Specification<EmployeeProject> getProjectsFromNames(List<String> projectNames) {
        return (root, query, criteriaBuilder) -> {
            if(CollectionUtils.isEmpty(projectNames)) {
                return criteriaBuilder.conjunction();
            }
            Join<EmployeeProject, Project> employeeProjectJoin = root.join("project");
            return employeeProjectJoin.get("name").in(projectNames);
        };
    }
}
