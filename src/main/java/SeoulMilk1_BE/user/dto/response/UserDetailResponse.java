package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.User;
import lombok.Builder;
import org.springframework.util.StringUtils;

import static SeoulMilk1_BE.user.domain.type.Role.*;

@Builder
public record UserDetailResponse(
    Long userId,
    String name,
    String role,
    String teamName,
    String loginId,
    String email,
    String phone,
    String bank,
    String account
) {
    public static UserDetailResponse from(User user) {
        UserDetailResponseBuilder response = UserDetailResponse.builder()
            .userId(user.getId())
            .name(user.getName())
            .loginId(user.getLoginId())
            .email(user.getEmail())
            .phone(formatPhoneNumber(user.getPhone()));

        if (user.getRole() == ADMIN) {
            response.role("관리자");
        } else if (user.getRole() == HQ_USER) {
            response.role("직원")
                    .teamName(user.getTeam().getName());
        } else {
            response.teamName(user.getTeam().getName())
                    .bank(user.getTeam().getBank())
                    .account(user.getTeam().getAccount());
        }

        return response.build();
    }

    private static String formatPhoneNumber(String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber) || phoneNumber.length() != 11) {
            return phoneNumber;
        }
        return phoneNumber.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
    }
}
