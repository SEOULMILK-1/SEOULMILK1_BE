package SeoulMilk1_BE.nts_tax.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CsSearchTaxResponseList(
        List<CsSearchTaxResponse> responseList
) {
    public static CsSearchTaxResponseList from(List<CsSearchTaxResponse> responseList) {
        return CsSearchTaxResponseList.builder()
                .responseList(responseList)
                .build();
    }
}
