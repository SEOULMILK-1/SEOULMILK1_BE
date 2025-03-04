package SeoulMilk1_BE.nts_tax.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    WAIT("승인대기"),
    APPROVE("승인됨"),
    REFUSED("반려됨"),
    DONE("지급결의"),
    ;

    private final String message;
}
