package SeoulMilk1_BE.nts_tax.dto.response;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.domain.type.Status;
import lombok.Builder;

@Builder
public record CsSearchTaxResponse(
        Long ntsTaxId,
        String title,
        String taxDate,
        String team,
        String name,
        Status status
) {
    public static CsSearchTaxResponse from(NtsTax ntsTax) {
        return CsSearchTaxResponse.builder()
                .ntsTaxId(ntsTax.getId())
                .title(ntsTax.getIssueDate())
                .taxDate(ntsTax.getIssueDate())
                .team(ntsTax.getSuDeptName())
                .name(ntsTax.getSuPersName())
                .status(ntsTax.getStatus())
                .build();
    }
}
