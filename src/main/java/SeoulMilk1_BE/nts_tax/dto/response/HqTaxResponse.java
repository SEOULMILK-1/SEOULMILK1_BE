package SeoulMilk1_BE.nts_tax.dto.response;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import lombok.Builder;

@Builder
public record HqTaxResponse(
        Long ntsTaxId,
        String title,
        String taxDate,
        String csName
) {
    public static HqTaxResponse from(NtsTax ntsTax) {
        return HqTaxResponse.builder()
                .ntsTaxId(ntsTax.getId())
                .title(ntsTax.getTitle())
                .taxDate(getFormattedTaxDate(ntsTax.getIssueDate()))
                .csName(ntsTax.getSuDeptName())
                .build();
    }

    private static String getFormattedTaxDate(String issueDate) {
        return issueDate.substring(0, 4) + "." + issueDate.substring(4, 6) + "." + issueDate.substring(6, 8);
    }
}
