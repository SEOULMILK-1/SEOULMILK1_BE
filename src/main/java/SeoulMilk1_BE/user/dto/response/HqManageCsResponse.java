package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.Team;
import lombok.Builder;

@Builder
public record HqManageCsResponse(
        Long csId,
        String csName
) {
    public static HqManageCsResponse from(Team team) {
        return HqManageCsResponse.builder()
                .csId(team.getId())
                .csName(team.getName())
                .build();
    }
}
