package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.user.domain.Team;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.dto.request.UpdateUserRequest;
import SeoulMilk1_BE.user.dto.response.UserDetailResponse;
import SeoulMilk1_BE.user.dto.response.UserResponse;
import SeoulMilk1_BE.user.exception.UserNotFoundException;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.USER_NOT_FOUND;
import static SeoulMilk1_BE.user.domain.type.Role.CS_USER;
import static SeoulMilk1_BE.user.util.UserConstants.UPDATE_SUCCESS;

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

    @Transactional
    public String updateUser(Long userId, UpdateUserRequest request) {
        User user = findUser(userId);
        user.updateUser(request);

        if (user.getRole() == CS_USER) {
            Team team = user.getTeam();
            team.updateTeam(request);
        }

        return UPDATE_SUCCESS.getMessage();
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
}
