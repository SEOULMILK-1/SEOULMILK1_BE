package SeoulMilk1_BE.payment_resolution.dto.response;

import SeoulMilk1_BE.payment_resolution.domain.type.Status;

import java.time.LocalDateTime;

public record PaymentResolutionListResponse(
        Long paymentResolutionId,
        Status status,
        String paymentResolutionName,
        LocalDateTime createdAt,
        String csName
) {

}
