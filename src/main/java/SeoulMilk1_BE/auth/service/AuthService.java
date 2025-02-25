package SeoulMilk1_BE.auth.service;

import SeoulMilk1_BE.auth.util.JwtTokenProvider;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public String getTestToken(Long userId) {
        return jwtTokenProvider.createAccessToken(userRepository.findById(userId).orElseThrow());
    }
}
