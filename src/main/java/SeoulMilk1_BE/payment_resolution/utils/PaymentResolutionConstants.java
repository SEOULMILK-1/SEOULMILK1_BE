package SeoulMilk1_BE.payment_resolution.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentResolutionConstants {
    CREATE_SUCCESS("지급결의서가 정상 생성되었습니다."),
    UPDATE_SUCCESS("지급결의서가 정상 수정되었습니다."),
    DELETE_SUCCESS("지급결의서가 정상 삭제되었습니다."),
    ;

    private final String message;
}
