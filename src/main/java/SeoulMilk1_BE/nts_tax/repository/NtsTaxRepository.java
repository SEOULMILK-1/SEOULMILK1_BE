package SeoulMilk1_BE.nts_tax.repository;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NtsTaxRepository extends JpaRepository<NtsTax, Long> {

    Optional<NtsTax> findByIssueId(String issueId);
}
