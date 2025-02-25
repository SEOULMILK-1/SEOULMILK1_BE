package SeoulMilk1_BE.nts_tax.domain.type;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;

public enum IpTypeCode {
    BUSINESS_REGISTRATION_NUMBER("01", "사업자등록번호"),
    RESIDENT_REGISTRATION_NUMBER("02", "주민등록번호"),
    FOREIGNER("03", "외국인");

    private final String code;
    private final String description;

    IpTypeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public static IpTypeCode fromCode(String code) {
        for (IpTypeCode type : IpTypeCode.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new GeneralException(ErrorStatus.IPTYPECODE_NOT_FOUND);
    }
}
