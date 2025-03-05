package SeoulMilk1_BE.user.dto.request;

public record UpdateUserRequest(
        Long employeeId,
        String email,
        String phone,
        String bank,
        String account
) {
}