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
        String status
) {
    public static HqSearchTaxResponse from(NtsTax ntsTax) {
        return HqSearchTaxResponse.builder()
                .ntsTaxId(ntsTax.getId())
                .title(ntsTax.getTitle())
                .taxDate(ntsTax.getIssueDate())
                .team(ntsTax.getTeam().getName())
                .status(ntsTax.getIsPaymentWritten() ? "반영" : "미반영")
                .build();
    }
}
