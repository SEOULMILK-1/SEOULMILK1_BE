package SeoulMilk1_BE.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "아이디를 입력해주세요")
        String loginId,
        @Email(message = "이메일 형식을 맞춰주세요")
        @NotBlank(message = "이메일을 입력해주세요")
        String email,
        @NotBlank(message = "전화번호를 입력해주세요")
        String phone,
        String bank,
        String account
) {
}