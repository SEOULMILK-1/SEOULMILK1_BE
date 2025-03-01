package SeoulMilk1_BE.auth.dto.request;

import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.domain.type.Role;
import SeoulMilk1_BE.user.domain.type.Team;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

public record SignUpRequest(
        Long employeeId,
        @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하로 입력해주세요")
        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@!%?&])[A-Za-z\\d@!%?&]{8,16}$",
                message = "비밀번호는 영문, 숫자, 특수문자의 조합으로 입력해주세요")
        String password,
        String name,
        @Email(message = "이메일 형식을 맞춰주세요")
        String email,
        String phone,
        Role role,
        Team team
) {
    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .employeeId(employeeId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .email(email)
                .phone(phone)
                .role(role)
                .team(team)
                .isAssigned(false)
                .build();
    }
}
