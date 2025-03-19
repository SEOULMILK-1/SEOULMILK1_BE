package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.User;
import lombok.Builder;

@Builder
public record HqSearchCsResponse(
        String csName,
        String phone,
        String email,
        String name
) {
    public static HqSearchCsResponse from(User user) {
        return HqSearchCsResponse.builder()
                .csName(user.getTeam().getName())
                .phone(formatPhoneNumber(user.getPhone()))
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    private static String formatPhoneNumber(String phone) {
        if (phone != null && phone.length() == 11) {
            return phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        }
        return phone;
    }
}
