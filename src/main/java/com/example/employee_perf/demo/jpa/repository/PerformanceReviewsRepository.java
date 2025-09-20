package com.example.employee_perf.demo.jpa.repository;

import com.example.employee_perf.demo.jpa.entity.Employee;
import com.example.employee_perf.demo.jpa.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceReviewsRepository extends JpaRepository<PerformanceReview, Long>, JpaSpecificationExecutor<PerformanceReview> {

    List<PerformanceReview> findFirst3PerformanceReviewByEmployeeOrderByReviewDateDesc(Employee employee);
}
