package SeoulMilk1_BE.auth.service;

import SeoulMilk1_BE.auth.dto.request.SignUpRequest;
import SeoulMilk1_BE.auth.util.JwtTokenProvider;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static SeoulMilk1_BE.user.util.UserConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public String getTestToken(Long userId) {
        return jwtTokenProvider.createAccessToken(userRepository.findById(userId).orElseThrow());
    }

    public String validateEmployeeId(Long employeeId) {
        String message = VALID_EMPLOYEE_ID.getMessage();

        if (userRepository.existsByEmployeeId(employeeId)) {
            message = DUPLICATE_EMPLOYEE_ID.getMessage();
        }

        return message;
    }

    @Transactional
    public String signUp(SignUpRequest request) {
        User user = request.toUser(passwordEncoder);
        userRepository.save(user);

        return PENDING.getMessage();
    }
}
