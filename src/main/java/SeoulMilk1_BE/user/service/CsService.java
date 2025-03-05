package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.domain.type.ValidStatus;
import SeoulMilk1_BE.nts_tax.dto.response.CsSearchTaxResponse;
import SeoulMilk1_BE.nts_tax.dto.response.CsSearchTaxResponseList;
import SeoulMilk1_BE.nts_tax.dto.response.CsTaxDetailResponse;
import SeoulMilk1_BE.nts_tax.exception.NtsTaxNotFoundException;
import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.TAX_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CsService {

    private final NtsTaxRepository ntsTaxRepository;

    public CsSearchTaxResponseList searchTax(Long userId, int page, int size, String startDate, String endDate, Long months, ValidStatus status) {
        String start = formatInputData(startDate);
        String end = formatInputData(endDate);

        Pageable pageable = PageRequest.of(page, size);
        Page<CsSearchTaxResponse> csTaxResponsePage = ntsTaxRepository.findTaxUsedInCS(pageable, userId, start, end, months, status);

        Long totalElements = csTaxResponsePage.getTotalElements();
        Integer totalPages = csTaxResponsePage.getTotalPages();
        List<CsSearchTaxResponse> responseList = csTaxResponsePage.getContent();

        return CsSearchTaxResponseList.of(totalElements, totalPages, responseList);
    }

    public CsTaxDetailResponse getTaxDetail(Long ntsTaxId) {
        NtsTax ntsTax = ntsTaxRepository.findById(ntsTaxId)
                .orElseThrow(() -> new NtsTaxNotFoundException(TAX_NOT_FOUND));

        return CsTaxDetailResponse.from(ntsTax);
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
