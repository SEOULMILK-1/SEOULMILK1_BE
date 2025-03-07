package SeoulMilk1_BE.auth.dto.request;

public record LoginRequest(
        String loginId,
        String password
) {
}