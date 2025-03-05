package SeoulMilk1_BE.nts_tax.dto.response;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.domain.type.ValidStatus;
import lombok.Builder;

@Builder
public record HqSearchTaxResponse(
        Long ntsTaxId,
        String title,
        String taxDate,
        String team,
        String name,
        ValidStatus status
) {
    public static HqSearchTaxResponse from(NtsTax ntsTax) {
        return HqSearchTaxResponse.builder()
                .ntsTaxId(ntsTax.getId())
                .title(ntsTax.getIssueDate())
                .taxDate(ntsTax.getIssueDate())
                .team(ntsTax.getSuDeptName())
                .name(ntsTax.getSuPersName())
                .status(ntsTax.getValidStatus())
                .build();
    }
}
