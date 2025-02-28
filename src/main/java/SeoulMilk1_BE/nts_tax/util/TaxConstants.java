package SeoulMilk1_BE.nts_tax.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaxConstants {
    UPDATE_SUCCESS("세금명세서 정보 수정이 되었습니다."),
    ;

    private final String message;
}
