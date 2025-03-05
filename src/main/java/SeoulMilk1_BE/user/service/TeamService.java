package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.user.domain.Team;
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
        Team team = teamRepository.findByName(name);
        if (team.getPaymentResolutionRecentMonth() == null || team.getPaymentResolutionRecentMonth() != month) {
            team.updatePaymentMonth(month);
        } else {
            team.updatePaymentCount();
        }

        return Optional.ofNullable(team.getPaymentResolutionCount());
    }
}
