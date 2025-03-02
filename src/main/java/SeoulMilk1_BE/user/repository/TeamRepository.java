package SeoulMilk1_BE.user.repository;

import SeoulMilk1_BE.user.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByNameContaining(String keyword);
}
