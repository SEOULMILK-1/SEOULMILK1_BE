package SeoulMilk1_BE.payment_resolution.dto.request;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.dto.response.ForPaymentTaxResponse;
import SeoulMilk1_BE.payment_resolution.domain.PaymentDetails;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionReadResponse;
import SeoulMilk1_BE.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        Long totalAllAmount,
        String hqUserName
) {
    public static PaymentResolutionRequest from(List<ForPaymentTaxResponse> ntsTaxList, User user) {
        ForPaymentTaxResponse ntsTaxDto = ntsTaxList.get(0);
        List<PaymentDetails> paymentDetailsDtoList = ntsTaxList.stream().map(
                n -> {
                    return PaymentDetails.builder()
                            .ntsId(n.ntsTaxId())
                            .ntsTaxNum(n.issueId())
                            .supplyAmount(n.chargeTotal())
                            .issueDate(n.createdAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                            .totalAmount(n.grandTotal())
                            .build();
                }
        ).collect(Collectors.toList());

        Long supplyAmount = paymentDetailsDtoList.stream().mapToLong(PaymentDetails::getSupplyAmount).sum();
        Long allAmount = paymentDetailsDtoList.stream().mapToLong(PaymentDetails::getTotalAmount).sum();

        return PaymentResolutionRequest.builder()
                .paymentRecipient(ntsTaxDto.team().getName())
                .recipientBusinessNumber(ntsTaxDto.team().getBusinessNumber())
                .totalPaymentAmount(allAmount)
                .paymentMethod("계좌이체")
                .paymentAccount(ntsTaxDto.team().getAccount())
                .paymentPrincipal(user.getTeam().getName())
                .principalBusinessNumber(user.getTeam().getBusinessNumber())
                .paymentDetails(paymentDetailsDtoList)
                .totalSupplyAmount(supplyAmount)
                .totalAllAmount(allAmount)
                .hqUserName(user.getName())
                .build();
    }
}
