package SeoulMilk1_BE.nts_tax.dto.request;

public record UpdateTaxRequest(
        String issueId,
        String suId,
        String ipId,
        String issueDate,
        String chargeTotal
) {
}
