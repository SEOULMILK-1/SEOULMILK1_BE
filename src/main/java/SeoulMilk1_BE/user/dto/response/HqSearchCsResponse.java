package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.Team;
import lombok.Builder;

@Builder
public record HqSearchCsResponse(
        String csName
) {
    public static HqSearchCsResponse from(Team team) {
        return HqSearchCsResponse.builder()
                .csName(team.getName())
                .build();
    }
}
