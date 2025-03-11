package SeoulMilk1_BE.payment_resolution.dto.response;

import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record PaymentResolutionListResponse(
        Long paymentResolutionId,
        String suDeptName,
        String paymentResolutionName,
        String createdAt,
        String hqUserName
) {
    public static PaymentResolutionListResponse from(PaymentResolution paymentResolution) {
        return PaymentResolutionListResponse.builder()
                .paymentResolutionId(paymentResolution.getId())
                .suDeptName(paymentResolution.getPaymentRecipient())
                .paymentResolutionName(paymentResolution.getName())
                .createdAt(paymentResolution.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .hqUserName(paymentResolution.getHqUserName())
                .build();
    }
}
