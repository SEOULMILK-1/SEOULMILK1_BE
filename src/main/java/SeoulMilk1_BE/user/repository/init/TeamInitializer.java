package SeoulMilk1_BE.user.repository.init;

import SeoulMilk1_BE.global.util.DummyDataInit;
import SeoulMilk1_BE.user.domain.Team;
import SeoulMilk1_BE.user.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class TeamInitializer implements ApplicationRunner {

    private final TeamRepository teamRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (teamRepository.count() > 0) {
            log.info("[Team] 더미 데이터 존재");
        } else {
            List<Team> teamList = new ArrayList<>();

            Team TEAM_CS = Team.builder()
                    .name("서울우유용산고객센터")
                    .build();

            Team TEAM_TP = Team.builder()
                    .name("서울우유태평고객센터")
                    .build();

            Team TEAM_DJ = Team.builder()
                    .name("서울우유대전송촌고객센터")
                    .build();

            Team TEAM_BM = Team.builder()
                    .name("서울우유협동조합보문고객센타")
                    .build();

            Team TEAM_DD = Team.builder()
                    .name("서울우유대덕대리점")
                    .build();

            teamList.add(TEAM_CS);
            teamList.add(TEAM_TP);
            teamList.add(TEAM_DJ);
            teamList.add(TEAM_BM);
            teamList.add(TEAM_DD);

            teamRepository.saveAll(teamList);
        }
    }
}
