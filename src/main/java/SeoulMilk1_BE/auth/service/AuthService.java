package SeoulMilk1_BE.auth.service;

import SeoulMilk1_BE.auth.dto.request.LoginRequest;
import SeoulMilk1_BE.auth.dto.request.SignUpRequest;
import SeoulMilk1_BE.auth.dto.response.LoginResponse;
import SeoulMilk1_BE.auth.util.JwtTokenProvider;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.exception.PasswordNotMatchException;
import SeoulMilk1_BE.user.exception.UserNotFoundException;
import SeoulMilk1_BE.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.PASSWORD_NOT_MATCH;
import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.USER_NOT_FOUND;
import static SeoulMilk1_BE.user.util.UserConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public String getTestToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        return jwtTokenProvider.createAccessToken(user);
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

    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmployeeId(request.employeeId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new PasswordNotMatchException(PASSWORD_NOT_MATCH);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        jwtTokenProvider.createRefreshToken(user, response);

        response.setHeader("Authorization", "Bearer " + accessToken);

        return LoginResponse.from(user);
    }
}
