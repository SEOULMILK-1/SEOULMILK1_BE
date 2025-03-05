package SeoulMilk1_BE.auth.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchCsNameResponseList(
        List<SearchCsNameResponse> responseList
) {
    public static SearchCsNameResponseList from(List<SearchCsNameResponse> responseList) {
        return SearchCsNameResponseList.builder()
                .responseList(responseList)
                .build();
    }
}
