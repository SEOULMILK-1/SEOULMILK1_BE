package SeoulMilk1_BE.nts_tax.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record HqSearchTaxResponseList(
        List<HqSearchTaxResponse> responseList
) {
    public static HqSearchTaxResponseList from(List<HqSearchTaxResponse> responseList) {
        return HqSearchTaxResponseList.builder()
                .responseList(responseList)
                .build();
    }
}
