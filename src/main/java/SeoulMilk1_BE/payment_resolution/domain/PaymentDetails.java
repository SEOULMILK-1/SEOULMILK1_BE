package SeoulMilk1_BE.payment_resolution.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDetails {
    private Long ntsId;
    private String ntsTaxNum;
    private Long supplyAmount;
    private String issueDate;
    private Long totalAmount;
}
