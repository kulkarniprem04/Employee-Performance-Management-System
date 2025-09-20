package com.example.employee_perf.demo.jpa.Specification;

import com.example.employee_perf.demo.jpa.entity.Department;
import com.example.employee_perf.demo.jpa.entity.Employee;
import com.example.employee_perf.demo.jpa.entity.EmployeeProject;
import com.example.employee_perf.demo.jpa.entity.PerformanceReview;
import com.example.employee_perf.demo.jpa.entity.Project;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class EmployeeSpecifications {

    public static Specification<Employee> belongsToDepartments(List<String> departmentNamess) {
        return (root, query, criteriaBuilder) -> {
            if(CollectionUtils.isEmpty(departmentNamess)) {
                return criteriaBuilder.conjunction();
            }
            Join<Employee, Department> departmentJoin = root.join("department");
            return departmentJoin.get("name").in(departmentNamess);
        };
    }

    public static Specification<Employee> getEmployeeFromIds(List<Long> EmployeeIds) {
        return (root, query, criteriaBuilder) -> {
            if(CollectionUtils.isEmpty(EmployeeIds)) {
                return criteriaBuilder.conjunction();
            }
            return root.get("id").in(EmployeeIds);
        };
    }

    public static Specification<Employee> getEmployeeFromReviewDate(Date reviewDate) {
        return (root, query, criteriaBuilder) -> {
            if(Objects.isNull(reviewDate)) {
                return criteriaBuilder.conjunction();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(reviewDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date startOfDay = cal.getTime();

            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date startOfNextDay = cal.getTime();

            Join<Employee, PerformanceReview> performanceReviewJoin = root.join("performanceReviews", JoinType.INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(performanceReviewJoin.get("reviewDate"), startOfDay),
                    criteriaBuilder.lessThan(performanceReviewJoin.get("reviewDate"), startOfNextDay)
            );
        };
    }
}
