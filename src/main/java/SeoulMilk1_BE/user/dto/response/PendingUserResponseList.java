package SeoulMilk1_BE.user.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PendingUserResponseList(
        List<PendingUserResponse> responseList
) {
    public static PendingUserResponseList from(List<PendingUserResponse> responseList) {
        return PendingUserResponseList.builder()
                .responseList(responseList)
                .build();
    }
}