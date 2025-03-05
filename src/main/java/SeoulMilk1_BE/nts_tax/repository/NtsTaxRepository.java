package SeoulMilk1_BE.nts_tax.repository;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NtsTaxRepository extends JpaRepository<NtsTax, Long>, NtsTaxRepositoryCustom {

    Optional<NtsTax> findByIssueId(String issueId);

    @Query("SELECT nts FROM NtsTax nts WHERE nts.issueDate LIKE %:yearMonth%")
    Page<NtsTax> findAllByIssueDateStartsWith(String yearMonth, Pageable pageable);

    @Query(
            "SELECT nts FROM NtsTax nts WHERE nts.modifiedAt > :deadline AND nts.status = 'APPROVE'"
    )
    List<NtsTax> findAllByPeriod(@Param("deadline") LocalDateTime deadline);

}
