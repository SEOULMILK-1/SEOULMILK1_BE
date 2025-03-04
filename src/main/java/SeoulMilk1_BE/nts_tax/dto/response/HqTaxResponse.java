package SeoulMilk1_BE.nts_tax.dto.response;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import lombok.Builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
public record HqTaxResponse(
        Long ntsTaxId,
        String title,
        String taxDate,
        String team,
        String name
) {
    public static HqTaxResponse from(NtsTax ntsTax) {
        return HqTaxResponse.builder()
                .ntsTaxId(ntsTax.getId())
                .title(getMonth() + " 세금계산서")
                .taxDate(getFormattedTaxDate(ntsTax.getIssueDate()))
                .team(ntsTax.getSuDeptName())
                .name(ntsTax.getSuPersName())
                .build();
    }

    private static String getMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(DateTimeFormatter.ofPattern("MM월"));
    }

    private static String getFormattedTaxDate(String issueDate) {
        return issueDate.substring(0, 4) + "." + issueDate.substring(4, 6) + "." + issueDate.substring(6, 8);
    }
}
