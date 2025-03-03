package SeoulMilk1_BE.auth.dto.response;

import SeoulMilk1_BE.user.domain.Team;
import lombok.Builder;

@Builder
public record SearchCsNameResponse(
        Long csId,
        String csName
) {
    public static SearchCsNameResponse from(Team team) {
        return SearchCsNameResponse.builder()
                .csId(team.getId())
                .csName(team.getName())
                .build();
    }
}
