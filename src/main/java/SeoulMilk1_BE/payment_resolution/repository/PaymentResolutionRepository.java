package SeoulMilk1_BE.payment_resolution.repository;

import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionListResponse;
import SeoulMilk1_BE.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PaymentResolutionRepository extends JpaRepository<PaymentResolution, Long> {
    Page<PaymentResolution> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT p FROM PaymentResolution p WHERE p.createdAt >= :deadline ORDER BY p.modifiedAt desc")
    Page<PaymentResolution> findListByDeadline(@Param("deadline") LocalDateTime deadline, Pageable pageable);
}
