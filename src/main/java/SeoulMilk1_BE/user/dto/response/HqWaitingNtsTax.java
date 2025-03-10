package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

@Builder
public record HqWaitingNtsTax(
        Long ntsTaxId,
        String suDeptName,
        String title,
        String createdAt,
        String csUserName
) {
    public static HqWaitingNtsTax of(NtsTax ntsTax) {
        return HqWaitingNtsTax.builder()
                .ntsTaxId(ntsTax.getId())
                .suDeptName(ntsTax.getSuDeptName())
                .title(ntsTax.getTitle())
                .createdAt(ntsTax.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .csUserName(ntsTax.getUser().getName())
                .build();
    }
}
