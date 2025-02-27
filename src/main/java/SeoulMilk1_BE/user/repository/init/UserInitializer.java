package SeoulMilk1_BE.user.repository.init;

import SeoulMilk1_BE.global.util.DummyDataInit;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static SeoulMilk1_BE.user.domain.type.Role.*;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            log.info("[User] 더미 데이터 존재");
        } else {
            List<User> userList = new ArrayList<>();

            User DUMMY_ADMIN = User.builder()
                    .employeeId(123456L)
                    .password(passwordEncoder.encode("password"))
                    .name("관리자")
                    .email("admin@naver.com")
                    .phone("01011112222")
                    .profileImageUrl("image.png")
                    .isAssigned(true)
                    .role(ADMIN)
                    .team("서울우유협동조합")
                    .build();

            User DUMMY_USER1 = User.builder()
                    .employeeId(123789L)
                    .password(passwordEncoder.encode("password"))
                    .name("김민철")
                    .email("mincheol@naver.com")
                    .phone("01033337777")
                    .profileImageUrl("image.png")
                    .isAssigned(true)
                    .role(HQ_USER)
                    .team("서울우유태평고객센터")
                    .build();

            User DUMMY_USER2 = User.builder()
                    .employeeId(456789L)
                    .password(passwordEncoder.encode("password"))
                    .name("이우정")
                    .email("woojeong@naver.com")
                    .phone("01055559999")
                    .profileImageUrl("image.png")
                    .isAssigned(true)
                    .role(CUSTOMER_USER)
                    .team("서울우유용인고객센터")
                    .build();

            userList.add(DUMMY_ADMIN);
            userList.add(DUMMY_USER1);
            userList.add(DUMMY_USER2);

            userRepository.saveAll(userList);
        }
    }
}
