package SeoulMilk1_BE.payment_resolution.dto.response;

import java.util.List;

public record PaymentResolutionListResponse(
        Long total,
        List<PaymentResolutionListDetailsResponse> results
) {
}
