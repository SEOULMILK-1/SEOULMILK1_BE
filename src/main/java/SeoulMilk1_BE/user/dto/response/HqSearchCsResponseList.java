package SeoulMilk1_BE.user.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record HqSearchCsResponseList(
        Long totalElements,
        Integer totalPages,
        List<HqSearchCsResponse> responseList
) {
    public static HqSearchCsResponseList of(Long totalElements, Integer totalPages, List<HqSearchCsResponse> responseList) {
        return HqSearchCsResponseList.builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .responseList(responseList)
                .build();
    }
}
