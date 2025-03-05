package SeoulMilk1_BE.user.repository.init;

import SeoulMilk1_BE.global.util.DummyDataInit;
import SeoulMilk1_BE.user.domain.Team;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.exception.TeamNotFoundException;
import SeoulMilk1_BE.user.repository.TeamRepository;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.TEAM_NOT_FOUND;
import static SeoulMilk1_BE.user.domain.type.Role.*;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@DummyDataInit
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            log.info("[User] 더미 데이터 존재");
        } else {
            Team TEAM1 = teamRepository.findById(1L)
                    .orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND));
            Team TEAM2 = teamRepository.findById(2L)
                    .orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND));
            Team TEAM3 = teamRepository.findById(3L)
                    .orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND));
            Team TEAM4 = teamRepository.findById(4L)
                    .orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND));

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
                    .build();

            User DUMMY_USER2 = User.builder()
                    .employeeId(456789L)
                    .password(passwordEncoder.encode("password"))
                    .name("이우정")
                    .email("woojeong@naver.com")
                    .phone("01055559999")
                    .profileImageUrl("image.png")
                    .isAssigned(true)
                    .role(CS_USER)
                    .team(TEAM1)
                    .build();

            User DUMMY_UNASSIGNED_USER1 = User.builder()
                    .employeeId(100001L)
                    .password(passwordEncoder.encode("password"))
                    .name("박지민")
                    .email("park.jimin@naver.com")
                    .phone("01011112233")
                    .profileImageUrl("image.png")
                    .isAssigned(false)
                    .role(HQ_USER)
                    .build();

            User DUMMY_UNASSIGNED_USER2 = User.builder()
                    .employeeId(100002L)
                    .password(passwordEncoder.encode("password"))
                    .name("이수진")
                    .email("lee.sujin@naver.com")
                    .phone("01022223344")
                    .profileImageUrl("image.png")
                    .isAssigned(false)
                    .role(CS_USER)
                    .team(TEAM2)
                    .build();

            User DUMMY_UNASSIGNED_USER3 = User.builder()
                    .employeeId(100003L)
                    .password(passwordEncoder.encode("password"))
                    .name("정우성")
                    .email("jung.woosung@naver.com")
                    .phone("01033334455")
                    .profileImageUrl("image.png")
                    .isAssigned(false)
                    .role(HQ_USER)
                    .build();

            User DUMMY_UNASSIGNED_USER4 = User.builder()
                    .employeeId(100004L)
                    .password(passwordEncoder.encode("password"))
                    .name("최영수")
                    .email("choi.youngsoo@naver.com")
                    .phone("01044445566")
                    .profileImageUrl("image.png")
                    .isAssigned(false)
                    .role(CS_USER)
                    .team(TEAM3)
                    .build();

            User DUMMY_UNASSIGNED_USER5 = User.builder()
                    .employeeId(100005L)
                    .password(passwordEncoder.encode("password"))
                    .name("김하늘")
                    .email("kim.hanul@naver.com")
                    .phone("01055556677")
                    .profileImageUrl("image.png")
                    .isAssigned(false)
                    .role(CS_USER)
                    .team(TEAM4)
                    .build();

            userList.add(DUMMY_ADMIN);
            userList.add(DUMMY_USER1);
            userList.add(DUMMY_USER2);
            userList.add(DUMMY_UNASSIGNED_USER1);
            userList.add(DUMMY_UNASSIGNED_USER2);
            userList.add(DUMMY_UNASSIGNED_USER3);
            userList.add(DUMMY_UNASSIGNED_USER4);
            userList.add(DUMMY_UNASSIGNED_USER5);

            userRepository.saveAll(userList);
        }
    }
}
