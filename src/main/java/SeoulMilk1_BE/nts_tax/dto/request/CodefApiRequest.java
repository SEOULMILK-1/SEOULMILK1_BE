package SeoulMilk1_BE.nts_tax.dto.request;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;

public record CodefApiRequest(
        String suId,
        String ipId,
        String issueId,
        String chargeTotal,
        String issueDate
) {
    public static CodefApiRequest from(NtsTax ntsTax) {
        return new CodefApiRequest(ntsTax.getSuId(), ntsTax.getIpId(), ntsTax.getIssueId(), Long.toString(ntsTax.getChargeTotal()), ntsTax.getIssueDate());
    }

}
