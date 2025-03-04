package SeoulMilk1_BE.payment_resolution.repository;

import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentResolutionRepository extends JpaRepository<PaymentResolution, Long> {
    Page<PaymentResolution> findAllByOrderByModifiedAtDesc(Pageable pageable);
}
