package SeoulMilk1_BE.nts_tax.dto.request;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import lombok.Builder;

@Builder
public record CodefApiRequest(
        String suId,
        String ipId,
        String issueId,
        String chargeTotal,
        String issueDate
) {
    public static CodefApiRequest from(NtsTax ntsTax) {
        return CodefApiRequest.builder()
                .suId(ntsTax.getSuId())
                .ipId(ntsTax.getIpId())
                .issueId(ntsTax.getIssueId())
                .chargeTotal(Long.toString(ntsTax.getChargeTotal()))
                .issueDate(ntsTax.getIssueDate())
                .build();
    }

}
