package SeoulMilk1_BE.payment_resolution.dto.response;

import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaymentResolutionListResponse(
        Long paymentResolutionId,
        String paymentResolutionName,
        LocalDateTime createdAt,
        String paymentRecipient
) {
    public static PaymentResolutionListResponse from(PaymentResolution paymentResolution) {
        return PaymentResolutionListResponse.builder()
                .paymentResolutionId(paymentResolution.getId())
                .paymentResolutionName(paymentResolution.getName())
                .createdAt(paymentResolution.getCreatedAt())
                .paymentRecipient(paymentResolution.getPaymentRecipient())
                .build();
    }
}
