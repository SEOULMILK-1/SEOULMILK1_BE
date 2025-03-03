package SeoulMilk1_BE.nts_tax.repository;

import SeoulMilk1_BE.nts_tax.dto.response.CsSearchTaxResponse;
import SeoulMilk1_BE.nts_tax.dto.response.HqSearchTaxResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NtsTaxRepositoryCustom {

    Page<HqSearchTaxResponse> findTaxUsedInHQ(Pageable pageable, String keyword, String startDate, String endDate, Long months);

    Page<CsSearchTaxResponse> findTaxUsedInCS(Pageable pageable, Long userId, String startDate, String endDate, Long months);
}
