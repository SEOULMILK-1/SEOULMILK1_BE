package SeoulMilk1_BE.user.repository;

import SeoulMilk1_BE.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmployeeId(Long aLong);
}
