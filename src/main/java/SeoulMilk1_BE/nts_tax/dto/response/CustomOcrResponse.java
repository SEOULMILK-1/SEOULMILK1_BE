package SeoulMilk1_BE.nts_tax.dto.response;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.domain.type.ValidStatus;
import lombok.Builder;
import org.springframework.util.StringUtils;

import java.text.NumberFormat;
import java.util.Locale;

@Builder
public record CustomOcrResponse(
        Long ntsTaxId,
        String issueId,
        String suId,
        String ipId,
        String issueDate,
        String chargeTotal,
        String title,
        ValidStatus status
) {
    public static CustomOcrResponse from(NtsTax ntsTax) {
        return CustomOcrResponse.builder()
                .ntsTaxId(ntsTax.getId())
                .issueId(formatIssueId(ntsTax.getIssueId()))
                .suId(formatSuAndIpId(ntsTax.getSuId()))
                .ipId(formatSuAndIpId(ntsTax.getIpId()))
                .issueDate(formatIssueDate(ntsTax.getIssueDate()))
                .chargeTotal(formatChargeTotal(ntsTax.getChargeTotal()))
                .title(ntsTax.getTitle())
                .status(ntsTax.getValidStatus())
                .build();
    }

    public static CustomOcrResponse from(String message) {
        return CustomOcrResponse.builder()
                .issueId(message)
                .build();
    }

    public static CustomOcrResponse from(String message, NtsTax ntsTax) {
        return CustomOcrResponse.builder()
                .ntsTaxId(ntsTax.getId())
                .issueId(message)
                .issueDate(formatIssueDate(ntsTax.getIssueDate()))
                .title(ntsTax.getTitle())
                .status(ntsTax.getValidStatus())
                .build();
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

    public static String formatIssueDate(String issueDate) {
        if (!StringUtils.hasText(issueDate) || issueDate.length() != 8) {
            return issueDate;
        }
        return issueDate.substring(0, 4) + "-" + issueDate.substring(4, 6) + "-" + issueDate.substring(6, 8);
    }

    public static String formatChargeTotal(Long chargeTotal) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        return numberFormat.format(chargeTotal);
    }
}
