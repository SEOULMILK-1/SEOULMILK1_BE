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
                .taxDate(getFormattedTaxDate(ntsTax))
                .team(ntsTax.getSuDeptName())
                .name(ntsTax.getSuPersName())
                .build();
    }

    private static String getMonth() {
        LocalDate currentDate = LocalDate.now();
        String month = currentDate.format(DateTimeFormatter.ofPattern("MM월"));
        return month;
    }

    private static String getFormattedTaxDate(NtsTax ntsTax) {
        String issueDate = ntsTax.getIssueDate();
        String formattedTaxDate = issueDate.substring(0, 4) + "." + issueDate.substring(4, 6) + "." + issueDate.substring(6, 8);
        return formattedTaxDate;
    }
}
