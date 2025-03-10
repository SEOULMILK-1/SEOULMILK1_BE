package SeoulMilk1_BE.nts_tax.repository;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.user.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NtsTaxRepository extends JpaRepository<NtsTax, Long>, NtsTaxRepositoryCustom {

    Optional<NtsTax> findByIssueId(String issueId);

    @Query("SELECT nts FROM NtsTax nts WHERE nts.validStatus = 'APPROVE' AND nts.isPaymentWritten = false")
    List<NtsTax> findAllByIsPaymentWritten();

    @Query("SELECT nts FROM NtsTax nts WHERE nts.modifiedAt > :deadline AND nts.validStatus = 'APPROVE'")
    List<NtsTax> findAllByPeriod(@Param("deadline") LocalDateTime deadline);

    @Query("SELECT COUNT(nts) FROM NtsTax nts WHERE nts.team = :team AND SUBSTRING(nts.issueDate, 1, 6) = :issueYearMonth")
    Long countByTeamAndIssueYearMonth(Team team, String issueYearMonth);

    Boolean existsByIssueId(String issueId);

    List<NtsTax> findByIsPaymentWrittenFalseAndValidStatusTrueOrderBySuDeptName();
}
