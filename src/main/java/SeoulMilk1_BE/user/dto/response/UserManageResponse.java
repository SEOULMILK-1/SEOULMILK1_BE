package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.domain.type.Role;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record UserManageResponse(
        Long userId,
        String loginId,
        String name,
        String phone,
        Role role,
        String csName,
        String createdAt,
        String isAssigned,
        String email
) {
    public static UserManageResponse from(User user) {
        return UserManageResponse.builder()
                .userId(user.getId())
                .loginId(user.getLoginId())
                .name(user.getName())
                .phone(formatPhoneNumber(user.getPhone()))
                .role(user.getRole())
                .csName(user.getTeam() != null ? user.getTeam().getName() : null)
                .createdAt(formatCreatedAt(user.getCreatedAt()))
                .isAssigned(user.getIsAssigned() ? "등록" : "미등록")
                .email(user.getEmail())
                .build();
    }

    private static String formatCreatedAt(LocalDateTime createdAt) {
        if (createdAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            return createdAt.format(formatter);
        }

        return String.valueOf(createdAt);
    }

    private static String formatPhoneNumber(String phone) {
        if (phone != null && phone.length() == 11) {
            return phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        }
        return phone;
    }
}