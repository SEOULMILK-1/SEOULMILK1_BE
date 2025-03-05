package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.nts_tax.domain.type.Status;
import SeoulMilk1_BE.nts_tax.dto.response.CsSearchTaxResponse;
import SeoulMilk1_BE.nts_tax.dto.response.CsSearchTaxResponseList;
import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CsService {

    private final NtsTaxRepository ntsTaxRepository;

    public CsSearchTaxResponseList searchTax(Long userId, int page, int size, String startDate, String endDate, Long months, Status status) {
        String start = formatInputData(startDate);
        String end = formatInputData(endDate);

        Pageable pageable = PageRequest.of(page, size);
        Page<CsSearchTaxResponse> csTaxResponsePage = ntsTaxRepository.findTaxUsedInCS(pageable, userId, start, end, months, status);

        Long totalElements = csTaxResponsePage.getTotalElements();
        Integer totalPages = csTaxResponsePage.getTotalPages();
        List<CsSearchTaxResponse> responseList = csTaxResponsePage.getContent();

        return CsSearchTaxResponseList.of(totalElements, totalPages, responseList);
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
