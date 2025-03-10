package SeoulMilk1_BE.payment_resolution.dto.request;

public record PaymentResolutionUpdateAccountRequest(
        Long id,
        String bank,
        String accountNumber
) {
}
