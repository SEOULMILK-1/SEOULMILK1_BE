package SeoulMilk1_BE.nts_tax.repository;

import SeoulMilk1_BE.nts_tax.dto.response.HqTaxResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NtsTaxRepositoryCustom {


    Page<HqTaxResponse> findTax(Pageable pageable, String keyword, String startDate, String endDate, Long months);
}
