package SeoulMilk1_BE.nts_tax.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record HqTaxResponseList(
        List<HqTaxResponse> responseList
) {
    public static HqTaxResponseList from(List<HqTaxResponse> responseList) {
        return HqTaxResponseList.builder()
                .responseList(responseList)
                .build();
    }
}
