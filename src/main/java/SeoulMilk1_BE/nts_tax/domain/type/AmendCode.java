package SeoulMilk1_BE.nts_tax.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AmendCode {
    MISTAKE_CORRECTION("01", "기재사항의 착오·정정"),
    PRICE_CHANGE("02", "공급가액 변동"),
    RETURN("03", "환입"),
    CONTRACT_TERMINATION("04", "계약의 해제"),
    POST_ISSUED_LC("05", "내국신용장 사후 개설"),
    CANCELLATION("06", "취소 발행"),
    ;

    private final String code;
    private final String description;

    public static AmendCode fromCode(String code) {
        for (AmendCode amendCode : AmendCode.values()) {
            if (amendCode.code.equals(code)) {
                return amendCode;
            }
        }
        throw new IllegalArgumentException("Unknown InvoiceCorrectionReason code: " + code);
    }
}
