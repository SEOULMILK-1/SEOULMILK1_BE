package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.dto.response.PendingUserResponse;
import SeoulMilk1_BE.user.dto.response.PendingUserResponseList;
import SeoulMilk1_BE.user.exception.UserNotFoundException;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.USER_NOT_FOUND;
import static SeoulMilk1_BE.user.util.UserConstants.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public PendingUserResponseList findPendingUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage = userRepository.findUsersByRoleHQOrAgencyAndNotAssigned(pageable);

        List<PendingUserResponse> responseList = userPage.stream()
                .map(PendingUserResponse::from)
                .toList();

        return PendingUserResponseList.of(userPage, responseList);
    }


    @Transactional
    public String approvePendingUser(Long userId) {
        User user = findUser(userId);
        user.assign();
        return APPROVED.getMessage();
    }

    @Transactional
    public String deleteUser(Long userId) {
        User user = findUser(userId);
        userRepository.delete(user);
        return DELETED.getMessage();
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
}
