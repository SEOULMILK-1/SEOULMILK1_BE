package SeoulMilk1_BE.nts_tax.domain.type;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PurpCode {
    RECEIPT("01", "영수"),
    CLAIM("02", "청구");

    private final String code;
    private final String description;

    public static PurpCode fromCode(String code) {
        for (PurpCode purpCode : PurpCode.values()) {
            if (purpCode.code.equals(code)) {
                return purpCode;
            }
        }

        throw new GeneralException(ErrorStatus.PURPCODE_NOT_FOUND);
    }
}
