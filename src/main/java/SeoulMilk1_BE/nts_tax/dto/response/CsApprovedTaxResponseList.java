package SeoulMilk1_BE.nts_tax.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CsApprovedTaxResponseList(
        List<CsApprovedTaxResponse> responseList
) {
    public static CsApprovedTaxResponseList from(List<CsApprovedTaxResponse> responseList) {
        return CsApprovedTaxResponseList.builder()
                .responseList(responseList)
                .build();
    }
}
