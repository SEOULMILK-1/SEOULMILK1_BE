package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.domain.type.Status;
import SeoulMilk1_BE.nts_tax.dto.response.HqSearchTaxResponse;
import SeoulMilk1_BE.nts_tax.dto.response.HqSearchTaxResponseList;
import SeoulMilk1_BE.nts_tax.dto.response.HqTaxResponse;
import SeoulMilk1_BE.nts_tax.dto.response.HqTaxResponseList;
import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepository;
import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepositoryCustom;
import SeoulMilk1_BE.user.dto.response.HqSearchCsNameResponse;
import SeoulMilk1_BE.user.dto.response.HqSearchCsNameResponseList;
import SeoulMilk1_BE.user.dto.response.HqSearchCsResponse;
import SeoulMilk1_BE.user.dto.response.HqSearchCsResponseList;
import SeoulMilk1_BE.user.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HqService {

    private final NtsTaxRepository ntsTaxRepository;
    private final NtsTaxRepositoryCustom ntsTaxRepositoryCustom;
    private final TeamRepository teamRepository;

    public HqTaxResponseList getTaxInfo(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String thisMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

        Page<NtsTax> ntsTaxList = ntsTaxRepository.findAllByIssueDateStartsWith(thisMonth, pageable);

        List<HqTaxResponse> responseList = ntsTaxList.stream()
                .map(HqTaxResponse::from)
                .toList();

        return HqTaxResponseList.from(responseList);
    }

    public HqSearchTaxResponseList searchTax(int page, int size, String keyword, String startDate, String endDate, Long months, Status status) {
        String start = formatInputData(startDate);
        String end = formatInputData(endDate);

        Pageable pageable = PageRequest.of(page, size);
        Page<HqSearchTaxResponse> hqTaxResponsePage = ntsTaxRepositoryCustom.findTaxUsedInHQ(pageable, keyword, start, end, months, status);

        List<HqSearchTaxResponse> responseList = hqTaxResponsePage.getContent();

        return HqSearchTaxResponseList.from(responseList);
    }

    public HqSearchCsNameResponseList searchCsName(String keyword) {
        List<HqSearchCsNameResponse> responseList = teamRepository.findByNameContaining(keyword).stream()
                .map(HqSearchCsNameResponse::from)
                .toList();

        return HqSearchCsNameResponseList.from(responseList);
    }

    public HqSearchCsResponseList searchCs(String keyword) {
        List<HqSearchCsResponse> responseList = teamRepository.findByNameContaining(keyword).stream()
                .map(HqSearchCsResponse::from)
                .toList();

        return HqSearchCsResponseList.from(responseList);
    }

    private static String formatInputData(String inputData) {
        if (!StringUtils.hasText(inputData) || inputData.isEmpty()) {
            return "";
        }

        return inputData.replace("-", "")
                .replace("/", "")
                .replace(" ", "")
                .replace("\n", "")
                .replace(".", "");
    }
}
