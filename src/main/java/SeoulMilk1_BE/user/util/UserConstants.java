package SeoulMilk1_BE.user.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserConstants {
    PENDING("승인 대기 처리가 되었습니다."),
    APPROVED("승인 처리가 되었습니다."),
    DELETED("회원 삭제가 완료되었습니다."),
    DUPLICATE_EMPLOYEE_ID("이미 가입된 사번입니다."),
    VALID_EMPLOYEE_ID("가입 가능한 사번입니다."),
    UPDATE_SUCCESS("내 정보 수정이 완료되었습니다."),
    ADD_MANAGE_CS_SUCCESS("담당 대리점이 추가되었습니다."),
    DELETE_MANAGE_CS_SUCCESS("담당 대리점이 삭제되었습니다."),
    ;

    private final String message;
}
