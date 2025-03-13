package SeoulMilk1_BE.nts_tax.dto.response;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import lombok.Builder;

@Builder
public record AdminSearchTaxResponse(
        Long ntsTaxId,
        String title,
        String taxDate,
        String team,
        String status
) {
    public static AdminSearchTaxResponse from(NtsTax ntsTax) {
        return AdminSearchTaxResponse.builder()
                .ntsTaxId(ntsTax.getId())
                .title(ntsTax.getTitle())
                .taxDate(ntsTax.getIssueDate())
                .team(ntsTax.getTeam().getName())
                .status(ntsTax.getIsPaymentWritten() ? "반영" : "미반영")
                .build();
    }
}
