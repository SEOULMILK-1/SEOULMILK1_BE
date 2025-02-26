package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.domain.type.Role;
import SeoulMilk1_BE.user.domain.type.Team;
import lombok.Builder;

@Builder
public record PendingUserResponse(
        Long userId,
        Long employeeId,
        String name,
        String email,
        String phone,
        Role role,
        Team team
) {
    public static PendingUserResponse from(User user) {
        return PendingUserResponse.builder()
                .userId(user.getId())
                .employeeId(user.getEmployeeId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .team(user.getTeam())
                .build();
    }
}