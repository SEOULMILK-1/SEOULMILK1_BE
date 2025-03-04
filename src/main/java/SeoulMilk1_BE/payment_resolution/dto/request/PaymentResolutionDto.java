package SeoulMilk1_BE.payment_resolution.dto.request;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.payment_resolution.domain.PaymentDetails;
import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.payment_resolution.domain.type.Status;
import SeoulMilk1_BE.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record PaymentResolutionDto(
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
        Long totalAllAmount
) {
    public static PaymentResolutionDto from(List<NtsTax> ntsTaxList, User user) {
        NtsTax ntsTax = ntsTaxList.get(0);
        List<PaymentDetails> paymentDetailsDtoList = ntsTaxList.stream().map(
                n -> {
                    return PaymentDetails.builder()
                            .ntsTaxNum(n.getIssueId())
                            .supplyAmount(n.getChargeTotal())
                            .issueDate(n.getModifiedAt())
                            .totalAmount(n.getGrandTotal())
                            .build();
                }
        ).collect(Collectors.toList());

        Long supplyAmount = paymentDetailsDtoList.stream().mapToLong(PaymentDetails::getSupplyAmount).sum();
        Long allAmount = paymentDetailsDtoList.stream().mapToLong(PaymentDetails::getTotalAmount).sum();

        return PaymentResolutionDto.builder()
                .paymentRecipient(ntsTax.getTeam().getName())
                .recipientBusinessNumber(ntsTax.getTeam().getBusinessNumber())
                .totalPaymentAmount(allAmount)
                .paymentMethod("계좌이체")
                .paymentAccount(ntsTax.getTeam().getAccount())
                .paymentPrincipal(user.getTeam().getName())
                .principalBusinessNumber(user.getTeam().getBusinessNumber())
                .approver(user.getName())
                .scheduledPaymentDate(LocalDateTime.now().plusDays(7))
                .createdAt(LocalDateTime.now())
                .paymentDetails(paymentDetailsDtoList)
                .totalSupplyAmount(supplyAmount)
                .totalAllAmount(allAmount)
                .build();
    }

    public static PaymentResolution of(PaymentResolutionDto request) {
        return PaymentResolution.builder()
                .paymentRecipient(request.paymentRecipient())
                .recipientBusinessNumber(request.recipientBusinessNumber())
                .totalPaymentAmount(request.totalPaymentAmount())
                .paymentMethod(request.paymentMethod())
                .paymentAccount(request.paymentAccount())
                .paymentPrincipal(request.paymentPrincipal())
                .principalBusinessNumber(request.principalBusinessNumber())
                .approver(request.approver())
                .scheduledPaymentDate(request.scheduledPaymentDate())
                .paymentDetailsList(request.paymentDetails())
                .totalSupplyAmount(request.totalSupplyAmount())
                .totalAllAmount(request.totalAllAmount())
                .status(Status.DONE)
                .build();
    }

    public static PaymentResolutionDto byId(PaymentResolution paymentResolution) {
        return PaymentResolutionDto.builder()
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
                .build();
    }
}
