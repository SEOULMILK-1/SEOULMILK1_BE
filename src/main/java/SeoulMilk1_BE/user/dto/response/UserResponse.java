package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.domain.type.Role;
import lombok.Builder;

@Builder
public record UserResponse(
        Long userId,
        String name,
        Role role
) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
