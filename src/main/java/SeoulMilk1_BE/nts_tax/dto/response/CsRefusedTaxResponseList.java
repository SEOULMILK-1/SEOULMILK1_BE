package SeoulMilk1_BE.nts_tax.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CsRefusedTaxResponseList(
        List<CsRefusedTaxResponse> responseList
) {
    public static CsRefusedTaxResponseList from(List<CsRefusedTaxResponse> responseList) {
        return CsRefusedTaxResponseList.builder()
                .responseList(responseList)
                .build();
    }
}
