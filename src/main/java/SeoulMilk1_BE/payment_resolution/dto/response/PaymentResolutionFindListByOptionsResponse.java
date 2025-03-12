package SeoulMilk1_BE.payment_resolution.dto.response;

import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;

import java.util.List;

public record PaymentResolutionFindListByOptionsResponse(
        Long count,
        List<PaymentResolution> paymentResolutionList
) {
}
