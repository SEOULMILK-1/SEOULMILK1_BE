package SeoulMilk1_BE.user.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record HqManageCsResponseList(
        List<HqManageCsResponse> responseList
) {
    public static HqManageCsResponseList from(List<HqManageCsResponse> responseList) {
        return HqManageCsResponseList.builder()
                .responseList(responseList)
                .build();
    }
}
