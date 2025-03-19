package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.domain.type.Role;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record PendingUserResponse(
        Long userId,
        String loginId,
        String name,
        String phone,
        Role role,
        String team,
        String createdAt
) {
    public static PendingUserResponse from(User user) {
        return PendingUserResponse.builder()
                .userId(user.getId())
                .loginId(user.getLoginId())
                .name(user.getName())
                .phone(formatPhoneNumber(user.getPhone()))
                .role(user.getRole())
                .team(user.getTeam() != null ? user.getTeam().getName() : null)
                .createdAt(formatCreatedAt(user.getCreatedAt()))
                .build();
    }

    private static String formatPhoneNumber(String phone) {
        if (phone != null && phone.length() == 11) {
            return phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        }
        return phone;
    }

    private static String formatCreatedAt(LocalDateTime createdAt) {
        if (createdAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            return createdAt.format(formatter);
        }

        return String.valueOf(createdAt);
    }
}