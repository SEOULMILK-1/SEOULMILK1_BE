package SeoulMilk1_BE.nts_tax.repository;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NtsTaxRepository extends JpaRepository<NtsTax, Long> {
}
