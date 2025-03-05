package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.domain.type.Role;
import lombok.Builder;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record UserManageResponse(
        Long userId,
        Long employeeId,
        String name,
        String phone,
        Role role,
        String csName,
        String createdAt,
        String isAssigned
) {
    public static UserManageResponse from(User user) {
        return UserManageResponse.builder()
                .userId(user.getId())
                .employeeId(user.getEmployeeId())
                .name(user.getName())
                .phone(formatPhone(user.getPhone()))
                .role(user.getRole())
                .csName(user.getTeam() != null ? user.getTeam().getName() : null)
                .createdAt(formatCreatedAt(user.getCreatedAt()))
                .isAssigned(user.getIsAssigned() ? "등록" : "미등록")
                .build();
    }

    private static String formatPhone(String phone) {
        if (StringUtils.hasText(phone) && phone.matches("\\d{10,11}")) {
            return phone.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
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