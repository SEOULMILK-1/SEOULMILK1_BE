package SeoulMilk1_BE.auth.service;

import SeoulMilk1_BE.auth.dto.request.LoginRequest;
import SeoulMilk1_BE.auth.dto.request.SignUpCSRequest;
import SeoulMilk1_BE.auth.dto.request.SignUpHQRequest;
import SeoulMilk1_BE.auth.dto.response.LoginResponse;
import SeoulMilk1_BE.auth.dto.response.SearchCsNameResponse;
import SeoulMilk1_BE.auth.dto.response.SearchCsNameResponseList;
import SeoulMilk1_BE.auth.util.JwtTokenProvider;
import SeoulMilk1_BE.user.domain.Team;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.exception.PasswordNotMatchException;
import SeoulMilk1_BE.user.exception.TeamNotFoundException;
import SeoulMilk1_BE.user.exception.UserNotFoundException;
import SeoulMilk1_BE.user.repository.TeamRepository;
import SeoulMilk1_BE.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.*;
import static SeoulMilk1_BE.user.util.UserConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;

    public String getTestToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        return jwtTokenProvider.createAccessToken(user);
    }

    public String validateLoginId(String loginId) {
        String message = VALID_EMPLOYEE_ID.getMessage();

        if (userRepository.existsByLoginId(loginId)) {
            message = DUPLICATE_EMPLOYEE_ID.getMessage();
        }

        return message;
    }

    public SearchCsNameResponseList searchCsName() {
        List<SearchCsNameResponse> responseList = teamRepository.findAll().stream()
                .map(SearchCsNameResponse::from)
                .toList();

        return SearchCsNameResponseList.from(responseList);
    }

    @Transactional
    public String signUpHQ(SignUpHQRequest request) {
        User user = request.toUser(passwordEncoder);
        userRepository.save(user);

        return PENDING.getMessage();
    }

    @Transactional
    public String signUpCS(SignUpCSRequest request) {
        Team team = teamRepository.findById(request.csId())
                .orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND));
        team.updateTeam(request.bank(), request.account());

        User user = request.toUser(passwordEncoder, team);
        userRepository.save(user);

        return PENDING.getMessage();
    }

    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByLoginId(request.loginId())
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
