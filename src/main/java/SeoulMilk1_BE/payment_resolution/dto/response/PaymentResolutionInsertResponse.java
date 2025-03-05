package SeoulMilk1_BE.payment_resolution.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaymentResolutionInsertResponse(
        Long paymentResolutionId,
        LocalDateTime updatedAt
) {
    public static PaymentResolutionInsertResponse of(Long id, LocalDateTime updatedAt) {
        return PaymentResolutionInsertResponse.builder()
                .paymentResolutionId(id)
                .updatedAt(updatedAt)
                .build();
    }
}
