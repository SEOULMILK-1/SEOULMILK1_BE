package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import SeoulMilk1_BE.user.domain.Team;
import SeoulMilk1_BE.user.exception.TeamNotFoundException;
import SeoulMilk1_BE.user.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional
    public Optional<Integer> findAboutPayment(String name, int month) {
        Team team = teamRepository.findByName(name)
                .orElseThrow(() -> new TeamNotFoundException(ErrorStatus.TEAM_NOT_FOUND));

        if (team.getPaymentResolutionRecentMonth() == null || team.getPaymentResolutionRecentMonth() != month) {
            team.updatePaymentMonth(month);
        } else {
            team.updatePaymentCount();
        }

        return Optional.ofNullable(team.getPaymentResolutionCount());
    }

    public Team findTeam(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));
    }
}
