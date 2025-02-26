package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public User findOne(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}
