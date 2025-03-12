package SeoulMilk1_BE.payment_resolution.repository;

import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentResolutionRepository extends JpaRepository<PaymentResolution, Long> {
}
