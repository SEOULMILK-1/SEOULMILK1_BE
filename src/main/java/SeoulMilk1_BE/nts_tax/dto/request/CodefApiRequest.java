package SeoulMilk1_BE.nts_tax.dto.request;

public record CodefApiRequest(
        String suId,
        String ipId,
        String issueId,
        String chargeTotal,
        String issueDate
) {
    public static CodefApiRequest of(String suId, String ipIp, String issueId, Long chargeTotal, String issueDate) {
        return new CodefApiRequest(suId, ipIp, issueId, Long.toString(chargeTotal), issueDate);
    }
}
