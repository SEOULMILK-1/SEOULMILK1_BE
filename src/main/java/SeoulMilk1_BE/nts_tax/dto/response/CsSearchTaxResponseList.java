package SeoulMilk1_BE.nts_tax.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CsSearchTaxResponseList(
        Long totalElements,
        Integer totalPages,
        List<CsSearchTaxResponse> responseList
) {
    public static CsSearchTaxResponseList of(Long totalElements, Integer totalPages, List<CsSearchTaxResponse> responseList) {
        return CsSearchTaxResponseList.builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responseList(responseList)
                .build();
    }
}
