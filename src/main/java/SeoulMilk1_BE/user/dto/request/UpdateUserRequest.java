package SeoulMilk1_BE.user.dto.request;

public record UpdateUserRequest(
        String loginId,
        String email,
        String phone,
        String bank,
        String account
) {
}