package SeoulMilk1_BE.nts_tax.repository;

import SeoulMilk1_BE.nts_tax.domain.type.ValidStatus;
import SeoulMilk1_BE.nts_tax.dto.response.CsSearchTaxResponse;
import SeoulMilk1_BE.nts_tax.dto.response.HqSearchTaxResponse;
import SeoulMilk1_BE.user.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NtsTaxRepositoryCustom {

    Page<HqSearchTaxResponse> findTaxUsedInHQ(Pageable pageable, String keyword, String startDate, String endDate, Long months, Boolean status, List<Team> teamList);

    Page<CsSearchTaxResponse> findTaxUsedInCS(Pageable pageable, Long userId, String startDate, String endDate, Long months, ValidStatus status);
}
