package SeoulMilk1_BE.post.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostConstants {
    PIN_SUCCESS("해당 게시글이 상단 고정되었습니다."),
    PIN_FAILED("상단 고정 가능한 게시글 수를 초과했습니다."),
    UN_PIN_SUCCESS("해당 게시글이 상단 고정 해제되었습니다."),
    ;

    private final String message;
}
