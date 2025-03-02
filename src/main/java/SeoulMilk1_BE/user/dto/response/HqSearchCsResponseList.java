package SeoulMilk1_BE.user.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record HqSearchCsResponseList(
        List<HqSearchCsResponse> responseList
) {
    public static HqSearchCsResponseList from(List<HqSearchCsResponse> responseList) {
        return HqSearchCsResponseList.builder()
                .responseList(responseList)
                .build();
    }
}
