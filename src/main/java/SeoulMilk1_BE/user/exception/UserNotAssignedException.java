package SeoulMilk1_BE.user.exception;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserNotAssignedException extends RuntimeException {
    private final ErrorStatus errorStatus;
}
