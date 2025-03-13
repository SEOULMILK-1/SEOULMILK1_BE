package SeoulMilk1_BE.nts_tax.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record AdminSearchTaxResponseList(
        Long totalElements,
        Integer totalPages,
        List<AdminSearchTaxResponse> responseList
) {
    public static AdminSearchTaxResponseList of(Long totalElements, Integer totalPages, List<AdminSearchTaxResponse> responseList) {
        return AdminSearchTaxResponseList.builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responseList(responseList)
                .build();
    }
}
