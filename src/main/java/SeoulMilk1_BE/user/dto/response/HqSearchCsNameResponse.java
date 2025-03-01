package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.Team;
import lombok.Builder;

@Builder
public record HqSearchCsNameResponse(
        String csName
) {
    public static HqSearchCsNameResponse from(Team team) {
        return HqSearchCsNameResponse.builder()
                .csName(team.getName())
                .build();
    }
}
