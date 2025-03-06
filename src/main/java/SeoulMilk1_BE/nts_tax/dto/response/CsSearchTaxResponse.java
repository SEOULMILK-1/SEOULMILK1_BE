package SeoulMilk1_BE.nts_tax.dto.response;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.domain.type.ValidStatus;
import lombok.Builder;

@Builder
public record CsSearchTaxResponse(
        Long ntsTaxId,
        String title,
        String taxDate,
        String team,
        ValidStatus status
) {
    public static CsSearchTaxResponse from(NtsTax ntsTax) {
        return CsSearchTaxResponse.builder()
                .ntsTaxId(ntsTax.getId())
                .title(ntsTax.getTitle())
                .taxDate(ntsTax.getIssueDate())
                .team(ntsTax.getTeam().getName())
                .status(ntsTax.getValidStatus())
                .build();
    }
}
