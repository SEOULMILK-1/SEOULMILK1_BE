package SeoulMilk1_BE.user.repository;

import SeoulMilk1_BE.user.domain.Team;
import SeoulMilk1_BE.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByLoginId(String loginId);

    @Query("SELECT u FROM User u WHERE (u.role = 'HQ_USER' OR u.role = 'CS_USER') AND u.isAssigned = false AND u.isDeleted = false")
    Page<User> findUsersByRoleAndIsAssigned(Pageable pageable);

    Optional<User> findByLoginId(String loginId);

    @Query("SELECT u FROM User u WHERE u.team IN :teamList AND u.isDeleted = false")
    Page<User> findByTeamInAndIsDeleted(List<Team> teamList, Pageable pageable);

    @Query("""
    SELECT u FROM User u 
    WHERE u.isDeleted = false 
    ORDER BY 
        CASE 
            WHEN u.isAssigned = false THEN 0 
            ELSE 1 
        END, 
        CASE 
            WHEN u.role = 'HQ_USER' THEN 0 
            WHEN u.role = 'CS_USER' THEN 1 
            ELSE 2 
        END, 
        u.createdAt ASC
    """)
    Page<User> findAllByIsDeleted(Pageable pageable);

}