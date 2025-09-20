package com.example.employee_perf.demo.jpa.Specification;

import com.example.employee_perf.demo.jpa.entity.PerformanceReview;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Component
public class PerformanceReviewSpecification {

    public static Specification<PerformanceReview> reviewForParticularDate(Date reviewDate) {
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

            return criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(root.get("reviewDate"), startOfDay),
                    criteriaBuilder.lessThan(root.get("reviewDate"), startOfNextDay)
            );
        };
    }
}
