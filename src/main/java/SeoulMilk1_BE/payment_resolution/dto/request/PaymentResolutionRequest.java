package SeoulMilk1_BE.payment_resolution.dto.request;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.payment_resolution.domain.PaymentDetails;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionReadResponse;
import SeoulMilk1_BE.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record PaymentResolutionRequest(
        String paymentRecipient,
        String recipientBusinessNumber,
        Long totalPaymentAmount,
        String paymentMethod,
        String paymentAccount,
        String paymentPrincipal,
        String principalBusinessNumber,
        String approver,
        LocalDateTime scheduledPaymentDate,
        List<PaymentDetails> paymentDetails,
        Long totalSupplyAmount,
        Long totalAllAmount
) {
    public static PaymentResolutionRequest from(List<NtsTax> ntsTaxList, User user) {
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

        return PaymentResolutionRequest.builder()
                .paymentRecipient(ntsTax.getTeam().getName())
                .recipientBusinessNumber(ntsTax.getTeam().getBusinessNumber())
                .totalPaymentAmount(allAmount)
                .paymentMethod("계좌이체")
                .paymentAccount(ntsTax.getTeam().getAccount())
                .paymentPrincipal(user.getTeam().getName())
                .principalBusinessNumber(user.getTeam().getBusinessNumber())
                .paymentDetails(paymentDetailsDtoList)
                .totalSupplyAmount(supplyAmount)
                .totalAllAmount(allAmount)
                .build();
    }
}
