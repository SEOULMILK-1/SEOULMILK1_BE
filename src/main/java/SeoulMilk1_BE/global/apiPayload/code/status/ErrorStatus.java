package SeoulMilk1_BE.global.apiPayload.code.status;

import SeoulMilk1_BE.global.apiPayload.BaseErrorCode;
import SeoulMilk1_BE.global.apiPayload.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // JWT 관련 에러
    JWT_ROLE_ERROR(HttpStatus.FORBIDDEN, "JWT401", "가진 권한으로는 실행할 수 없는 기능입니다."),
    JWT_AUTH_ERROR(HttpStatus.UNAUTHORIZED, "JWT402", "로그인 후 다시 접근해주시기 바랍니다."),

    // 유저 관련 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER400", "유저를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "USER401", "비밀번호가 일치하지 않습니다."),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "USER402", "부서를 찾을 수 없습니다."),

    // S3 이미지 업로드 관련
    FILE_NOT_UPLOADED(HttpStatus.BAD_REQUEST, "S3401", "이미지를 업로드 할 수 없습니다."),
    FILE_IS_EMPTY(HttpStatus.BAD_REQUEST, "S3402", "파일이 비어있습니다."),
    FILE_NOT_IMAGE(HttpStatus.BAD_REQUEST, "S3403", "이미지 파일만 업로드 가능합니다."),
    IO_EXCEPTION_ON_IMAGE_DELETE(HttpStatus.BAD_REQUEST, "S34004", "삭제 중 에러가 발생했습니다."),
    INVALID_URL(HttpStatus.BAD_REQUEST, "S3405", "유효하지 않은 url입니다."),

    // 세금 계산서 관련 에러
    TYPECODE_NOT_FOUND(HttpStatus.NOT_FOUND, "TAX400", "해당 구분자를 찾을 수 없습니다."),
    PURPCODE_NOT_FOUND(HttpStatus.NOT_FOUND, "TAX401", "해당 지시자를 찾을 수 없습니다."),
    AMENDCODE_NOT_FOUND(HttpStatus.NOT_FOUND, "TAX402", "해당 사유 코드를 찾을 수 없습니다."),
    IPTYPECODE_NOT_FOUND(HttpStatus.NOT_FOUND, "TAX403", "해당 구분 코드를 찾을 수 없습니다."),
    TAX_NOT_FOUND(HttpStatus.NOT_FOUND, "TAX404", "해당 세금 계산서를 찾을 수 없습니다."),
    OCR_LOAD_ERROR(HttpStatus.BAD_REQUEST, "TAX405", "OCR 로드 중 에러가 발생했습니다."),
    ENCRYPTION_FAILED(HttpStatus.BAD_REQUEST, "TAX406", "RSA 암호화 중 에러가 발생했습니다."),

    // 지급결의서 관련 에러
    PAYMENT_RESOLUTION_NOT_FOUND(HttpStatus.NOT_FOUND, "PAY400", "해당 지급결의서를 찾을 수 없습니다."),

    // 게시글 관련 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST400", "해당 게시글을 찾을 수 없습니다."),
    COMMENT_IS_EXIST(HttpStatus.NOT_FOUND, "POST401", "해당 게시글에 등록된 댓글이 존재합니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}