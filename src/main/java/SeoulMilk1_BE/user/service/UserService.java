package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.dto.response.PendingUserResponse;
import SeoulMilk1_BE.user.dto.response.PendingUserResponseList;
import SeoulMilk1_BE.user.exception.UserNotFoundException;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.USER_NOT_FOUND;
import static SeoulMilk1_BE.user.util.UserConstants.APPROVED;
import static SeoulMilk1_BE.user.util.UserConstants.REJECTED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public PendingUserResponseList findPendingUsers() {
        List<PendingUserResponse> responseList = userRepository.findUsersByRoleHQOrAgencyAndNotAssigned().stream()
                .map(PendingUserResponse::from)
                .toList();

        return PendingUserResponseList.from(responseList);
    }

    @Transactional
    public String approvePendingUser(Long userId) {
        User user = findUser(userId);
        user.assign();
        // 승인된 유저에게 알림 가도록 기능 구현
        return APPROVED.getMessage();
    }

    @Transactional
    public String rejectPendingUser(Long userId) {
        User user = findUser(userId);
        // 승인 거절된 유저에게 알림 가도록 기능 구현
        // 승인 거절된 유저를 DB에서 삭제할지 그냥 isAssigned = false로 할지 추후 결정후 구현
        // isAssigned = false로 하면 승인 대기에 남아있는 문제점 있긴 함
        return REJECTED.getMessage();
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
}
