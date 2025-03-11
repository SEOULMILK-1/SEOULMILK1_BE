package SeoulMilk1_BE.payment_resolution.repository;

import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PaymentResolutionRepositoryCustom {
    Page<PaymentResolution> findListByOptions(
            Pageable pageable,
            @Param("suDeptName") String suDeptName,
            @Param("startDate") LocalDateTime startDateTime,
            @Param("endDate") LocalDateTime endDateTime,
            @Param("deadline") LocalDateTime deadline
    );
}
