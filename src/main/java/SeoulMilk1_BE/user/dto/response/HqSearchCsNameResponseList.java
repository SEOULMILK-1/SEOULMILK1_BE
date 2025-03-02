package SeoulMilk1_BE.user.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record HqSearchCsNameResponseList(
        List<HqSearchCsNameResponse> responseList
) {
    public static HqSearchCsNameResponseList from(List<HqSearchCsNameResponse> responseList) {
        return HqSearchCsNameResponseList.builder()
                .responseList(responseList)
                .build();
    }
}
