package SeoulMilk1_BE.nts_tax.domain.type;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;

public enum TypeCode {
    GENERAL_TAX_INVOICE("0101", "일반 세금계산서"),
    ZERO_RATE_TAX_INVOICE("0102", "영세율 세금계산서"),
    COMMISSIONED_TAX_INVOICE("0103", "위수탁 세금계산서"),
    IMPORT_TAX_INVOICE("0104", "수입 세금계산서"),
    ZERO_RATE_COMMISSIONED_TAX_INVOICE("0105", "영세율 위수탁 세금계산서"),
    REVISED_GENERAL_TAX_INVOICE("0201", "수정 일반 세금계산서"),
    REVISED_ZERO_RATE_TAX_INVOICE("0202", "수정 영세율 세금계산서"),
    REVISED_COMMISSIONED_TAX_INVOICE("0203", "수정 위수탁 세금계산서"),
    REVISED_IMPORT_TAX_INVOICE("0204", "수정 수입 세금계산서"),
    REVISED_ZERO_RATE_COMMISSIONED_TAX_INVOICE("0205", "수정 영세율 위수탁 세금계산서"),
    GENERAL_STATEMENT("0301", "일반 계산서"),
    COMMISSIONED_STATEMENT("0303", "위수탁 계산서"),
    IMPORT_STATEMENT("0304", "수입 계산서"),
    REVISED_GENERAL_STATEMENT("0401", "수정 일반 계산서"),
    REVISED_COMMISSIONED_STATEMENT("0403", "수정 위수탁 계산서");

    private final String code;
    private final String description;

    TypeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static TypeCode fromCode(String code) {
        for (TypeCode type : TypeCode.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }

        throw new GeneralException(ErrorStatus.TYPECODE_NOT_FOUND);
    }
}
