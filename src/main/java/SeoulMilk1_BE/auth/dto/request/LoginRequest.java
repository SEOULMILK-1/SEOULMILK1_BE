package SeoulMilk1_BE.auth.dto.request;

public record LoginRequest(
        Long employeeId,
        String password
) {
}