package SeoulMilk1_BE.nts_tax.repository;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.dto.response.ForPaymentTaxResponse;
import SeoulMilk1_BE.user.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NtsTaxRepository extends JpaRepository<NtsTax, Long>, NtsTaxRepositoryCustom {

    Optional<NtsTax> findByIssueId(String issueId);

    @Query("SELECT nts FROM NtsTax nts WHERE nts.validStatus = 'APPROVE' AND nts.isPaymentWritten = false AND nts.team IN :teams")
    List<NtsTax> findAllByIsPaymentWrittenAndManageCs(List<Team> teams);

    @Query("SELECT nts FROM NtsTax nts WHERE nts.modifiedAt > :deadline AND nts.validStatus = 'APPROVE'")
    List<NtsTax> findAllByPeriod(@Param("deadline") LocalDateTime deadline);

    @Query("SELECT COUNT(nts) FROM NtsTax nts WHERE nts.team = :team AND SUBSTRING(nts.issueDate, 1, 6) = :issueYearMonth")
    Long countByTeamAndIssueYearMonth(Team team, String issueYearMonth);

    Boolean existsByIssueId(String issueId);

    @Query("select nts FROM NtsTax nts WHERE nts.isPaymentWritten = false AND nts.validStatus = 'APPROVE' order by nts.suDeptName")
    List<NtsTax> findByIsPaymentWritten();

    @Query("SELECT new SeoulMilk1_BE.nts_tax.dto.response.ForPaymentTaxResponse(" +
            "n.suId, n.ipId, n.issueId, n.createdAt, n.chargeTotal, n.grandTotal, n.team, n.id) " +
            "FROM NtsTax n WHERE n.isPaymentWritten = false AND n.validStatus = 'APPROVE'")
    List<ForPaymentTaxResponse> findByIsNotWritten();

    @Query("SELECT nts FROM NtsTax nts WHERE nts.user.id = :userId AND SUBSTRING(nts.issueDate, 1, 6) = :thisMonth AND nts.validStatus = 'REFUSED'")
    List<NtsTax> findThisMonthRefusedTax(Long userId, String thisMonth);

    @Query("SELECT nts FROM NtsTax nts WHERE nts.user.id = :userId AND SUBSTRING(nts.issueDate, 1, 6) = :thisMonth AND nts.validStatus = 'APPROVE'")
    List<NtsTax> findThisMonthApprovedTax(Long userId, String thisMonth);
}
