package SeoulMilk1_BE.payment_resolution.dto.response;

import java.time.LocalDateTime;

public record PaymentResolutionListResponse(
        Long paymentResolutionId,
        String paymentResolutionName,
        LocalDateTime createdAt,
        String paymentRecipient
) {

}
