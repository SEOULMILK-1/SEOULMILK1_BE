package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.User;
import lombok.Builder;

@Builder
public record HqSearchCsResponse(
        String csName,
        String phone,
        String email,
        String name
) {
    public static HqSearchCsResponse from(User user) {
        return HqSearchCsResponse.builder()
                .csName(user.getTeam().getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
