package SeoulMilk1_BE.auth.dto.response;

import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.domain.type.Role;
import lombok.Builder;

@Builder
public record LoginResponse(
        Role role
) {
    public static LoginResponse from(User user) {
        return LoginResponse.builder()
                .role(user.getRole())
                .build();
    }
}