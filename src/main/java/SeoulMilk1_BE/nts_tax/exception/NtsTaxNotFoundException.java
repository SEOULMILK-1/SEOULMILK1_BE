package SeoulMilk1_BE.nts_tax.exception;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NtsTaxNotFoundException extends RuntimeException {
    private final ErrorStatus errorStatus;
}
