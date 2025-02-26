package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.user.dto.response.PendingUserResponse;
import SeoulMilk1_BE.user.dto.response.PendingUserResponseList;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
