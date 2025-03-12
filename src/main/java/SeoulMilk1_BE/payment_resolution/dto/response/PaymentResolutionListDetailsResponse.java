package SeoulMilk1_BE.payment_resolution.dto.response;

import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

@Builder
public record PaymentResolutionListDetailsResponse(
        Long paymentResolutionId,
        String suDeptName,
        String paymentResolutionName,
        String createdAt,
        String hqUserName
) {
    public static PaymentResolutionListDetailsResponse from(PaymentResolution paymentResolution) {
        return PaymentResolutionListDetailsResponse.builder()
                .paymentResolutionId(paymentResolution.getId())
                .suDeptName(paymentResolution.getPaymentRecipient())
                .paymentResolutionName(paymentResolution.getName())
                .createdAt(paymentResolution.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .hqUserName(paymentResolution.getHqUserName())
                .build();
    }
}
