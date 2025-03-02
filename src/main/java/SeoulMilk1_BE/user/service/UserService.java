package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.dto.response.UserDetailResponse;
import SeoulMilk1_BE.user.dto.response.UserResponse;
import SeoulMilk1_BE.user.exception.UserNotFoundException;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUserInfo(Long userId) {
        User user = findUser(userId);
        return UserResponse.from(user);
    }

    public UserDetailResponse getUserDetail(Long userId) {
        User user = findUser(userId);
        return UserDetailResponse.from(user);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
}
