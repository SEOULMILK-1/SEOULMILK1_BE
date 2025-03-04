package SeoulMilk1_BE.nts_tax.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaxConstants {
    UPDATE_SUCCESS("세금명세서 정보 수정이 되었습니다."),
    DELETE_SUCCESS("해당 세금명세서가 삭제 되었습니다."),
    VALIDATE_SUCCESS("세금계산서 검증이 완료되었습니다"),
    ;

    private final String message;
}
