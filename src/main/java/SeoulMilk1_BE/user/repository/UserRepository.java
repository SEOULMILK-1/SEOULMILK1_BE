package SeoulMilk1_BE.user.repository;

import SeoulMilk1_BE.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmployeeId(Long employeeId);

    @Query("SELECT u FROM User u WHERE (u.role = 'HQ_USER' OR u.role = 'AGENCY_USER') AND u.isAssigned = false")
    Page<User> findUsersByRoleHQOrAgencyAndNotAssigned(Pageable pageable);

    Optional<User> findByEmployeeId(Long employeeId);
}