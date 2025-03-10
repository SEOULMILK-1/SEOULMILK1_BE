package SeoulMilk1_BE.nts_tax.dto.response;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import lombok.Builder;

@Builder
public record CsApprovedTaxResponse(
        Long ntsTaxId,
        String title,
        String taxDate,
        String name
) {
    public static CsApprovedTaxResponse from(NtsTax ntsTax) {
        return CsApprovedTaxResponse.builder()
                .ntsTaxId(ntsTax.getId())
                .title(ntsTax.getTitle())
                .taxDate(formatTaxDate(ntsTax.getIssueDate()))
                .name(ntsTax.getUser().getName())
                .build();
    }

    private static String formatTaxDate(String issueDate) {
        return issueDate.substring(0, 4) + "." + issueDate.substring(4, 6) + "." + issueDate.substring(6, 8);
    }
}
