package SeoulMilk1_BE.payment_resolution.dto.response;

import SeoulMilk1_BE.payment_resolution.domain.PaymentDetails;
import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.payment_resolution.dto.request.PaymentResolutionRequest;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PaymentResolutionReadResponse(
        String name,
        String paymentRecipient,
        String recipientBusinessNumber,
        Long totalPaymentAmount,
        String paymentMethod,
        String paymentAccount,
        String paymentPrincipal,
        String principalBusinessNumber,
        String approver,
        LocalDateTime scheduledPaymentDate,
        LocalDateTime createdAt,
        List<PaymentDetails> paymentDetails,
        Long totalSupplyAmount,
        Long totalAllAmount,
        String hqUserName
) {
    public static PaymentResolution of(int count, PaymentResolutionRequest request) {
        String name = request.paymentRecipient() + " " + LocalDateTime.now().getYear() + "년 " + LocalDateTime.now().getMonthValue() + "월 지급결의서(" + count + ")";
        return PaymentResolution.builder()
                .name(name)
                .paymentRecipient(request.paymentRecipient())
                .recipientBusinessNumber(request.recipientBusinessNumber())
                .totalPaymentAmount(request.totalPaymentAmount())
                .paymentMethod(request.paymentMethod())
                .paymentAccount(request.paymentAccount())
                .paymentPrincipal(request.paymentPrincipal())
                .principalBusinessNumber(request.principalBusinessNumber())
                .paymentDetailsList(request.paymentDetails())
                .totalSupplyAmount(request.totalSupplyAmount())
                .totalAllAmount(request.totalAllAmount())
                .hqUserName(request.hqUserName())
                .build();
    }

    public static PaymentResolutionReadResponse byId(PaymentResolution paymentResolution) {
        return PaymentResolutionReadResponse.builder()
                .name(paymentResolution.getName())
                .paymentRecipient(paymentResolution.getPaymentRecipient())
                .recipientBusinessNumber(paymentResolution.getRecipientBusinessNumber())
                .totalPaymentAmount(paymentResolution.getTotalPaymentAmount())
                .paymentMethod("계좌이체")
                .paymentAccount(paymentResolution.getPaymentAccount())
                .paymentPrincipal(paymentResolution.getPaymentPrincipal())
                .principalBusinessNumber(paymentResolution.getPrincipalBusinessNumber())
                .approver(paymentResolution.getApprover())
                .scheduledPaymentDate(paymentResolution.getScheduledPaymentDate())
                .createdAt(paymentResolution.getModifiedAt())
                .paymentDetails(paymentResolution.getPaymentDetailsList())
                .totalSupplyAmount(paymentResolution.getTotalSupplyAmount())
                .totalAllAmount(paymentResolution.getTotalAllAmount())
                .hqUserName(paymentResolution.getHqUserName())
                .build();
    }
}
