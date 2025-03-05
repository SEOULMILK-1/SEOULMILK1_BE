package SeoulMilk1_BE.nts_tax.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record HqSearchTaxResponseList(
        Long totalElements,
        Integer totalPages,
        List<HqSearchTaxResponse> responseList
) {
    public static HqSearchTaxResponseList of(Long totalElements, Integer totalPages, List<HqSearchTaxResponse> responseList) {
        return HqSearchTaxResponseList.builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responseList(responseList)
                .build();
    }
}
