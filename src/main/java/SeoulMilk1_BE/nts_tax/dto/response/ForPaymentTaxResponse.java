package SeoulMilk1_BE.nts_tax.dto.response;

import SeoulMilk1_BE.user.domain.Team;

import java.time.LocalDateTime;

public record ForPaymentTaxResponse(
        String suId,
        String ipId,
        String issueId,
        LocalDateTime createdAt,
        Long chargeTotal,
        Long grandTotal,
        Team team,
        Long ntsTaxId
) {
    public ForPaymentTaxResponse {
    }
}
