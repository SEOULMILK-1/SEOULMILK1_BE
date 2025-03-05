package SeoulMilk1_BE.nts_tax.dto.response;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import lombok.Builder;
import org.springframework.util.StringUtils;

import java.text.NumberFormat;
import java.util.Locale;

@Builder
public record HqTaxDetailResponse(
        String status,
        String title,
        String taxImageUrl,
        String issueId,
        String suId,
        String ipId,
        String taxDate,
        String chargeTotal
) {
    public static HqTaxDetailResponse from(NtsTax ntsTax) {
        return HqTaxDetailResponse.builder()
                .status(ntsTax.getIsPaymentWritten() ? "반영" : "미반영")
                .title(getTitle(ntsTax.getSuDeptName(), ntsTax.getIssueDate()))
                .taxImageUrl(ntsTax.getTaxImgUrl())
                .issueId(formatIssueId(ntsTax.getIssueId()))
                .suId(formatSuAndIpId(ntsTax.getSuId()))
                .ipId(formatSuAndIpId(ntsTax.getIpId()))
                .taxDate(getFormattedTaxDate(ntsTax.getIssueDate()))
                .chargeTotal(formatChargeTotal(ntsTax.getChargeTotal()))
                .build();
    }

    private static String getTitle(String suDeptName, String issueDate) {
        String year = issueDate.substring(0, 4);
        String month = issueDate.substring(4, 6);

        return String.format("%s %s년 %s월 세금계산서", suDeptName, year, month);
    }

    private static String formatIssueId(String issueId) {
        if (!StringUtils.hasText(issueId) || issueId.length() != 24) {
            return issueId;
        }

        return issueId.substring(0, 8) + "-" +
                issueId.substring(8, 14) + "-" +
                issueId.substring(14, 24);
    }

    private static String formatSuAndIpId(String id) {
        if (!StringUtils.hasText(id) || id.length() != 10) {
            return id;
        }

        return id.substring(0, 3) + "-" +
                id.substring(3, 5) + "-" +
                id.substring(5, 10);
    }

    private static String getFormattedTaxDate(String issueDate) {
        return issueDate.substring(0, 4) + "." + issueDate.substring(4, 6) + "." + issueDate.substring(6, 8);
    }

    private static String formatChargeTotal(Long chargeTotal) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);
        return numberFormat.format(chargeTotal);
    }
}
