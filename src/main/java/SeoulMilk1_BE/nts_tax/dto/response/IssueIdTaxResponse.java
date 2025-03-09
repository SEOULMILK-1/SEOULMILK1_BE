package SeoulMilk1_BE.nts_tax.dto.response;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import lombok.Builder;

@Builder
public record IssueIdTaxResponse(
        Boolean isExist,
        NtsTax ntsTax
) {
    public static IssueIdTaxResponse of(Boolean isExist, NtsTax ntsTax) {
        return IssueIdTaxResponse.builder()
                .isExist(isExist)
                .ntsTax(ntsTax)
                .build();
    }
}
