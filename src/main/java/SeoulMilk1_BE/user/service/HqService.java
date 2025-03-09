package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.dto.response.*;
import SeoulMilk1_BE.nts_tax.exception.NtsTaxNotFoundException;
import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepository;
import SeoulMilk1_BE.user.domain.Team;
import SeoulMilk1_BE.user.dto.response.HqSearchCsNameResponse;
import SeoulMilk1_BE.user.dto.response.HqSearchCsNameResponseList;
import SeoulMilk1_BE.user.dto.response.HqSearchCsResponse;
import SeoulMilk1_BE.user.dto.response.HqSearchCsResponseList;
import SeoulMilk1_BE.user.repository.TeamRepository;
import SeoulMilk1_BE.user.repository.UserRepository;
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

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.TAX_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HqService {

    private final NtsTaxRepository ntsTaxRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public HqTaxResponseList getTaxInfo() {
        List<HqTaxResponse> responseList = ntsTaxRepository.findAllByIsPaymentWritten().stream()
                .map(HqTaxResponse::from)
                .toList();

        return HqTaxResponseList.from(responseList);
    }

    public HqSearchTaxResponseList searchTax(int page, int size, String keyword, String startDate, String endDate, Long months, Boolean status) {
        String start = formatInputData(startDate);
        String end = formatInputData(endDate);

        Pageable pageable = PageRequest.of(page, size);
        Page<HqSearchTaxResponse> hqTaxResponsePage = ntsTaxRepository.findTaxUsedInHQ(pageable, keyword, start, end, months, status);

        Long totalElements = hqTaxResponsePage.getTotalElements();
        Integer totalPages = hqTaxResponsePage.getTotalPages();
        List<HqSearchTaxResponse> responseList = hqTaxResponsePage.getContent();

        return HqSearchTaxResponseList.of(totalElements, totalPages, responseList);
    }

    public HqTaxDetailResponse getTaxDetail(Long ntsTaxId) {
        NtsTax ntsTax = ntsTaxRepository.findById(ntsTaxId)
                .orElseThrow(() -> new NtsTaxNotFoundException(TAX_NOT_FOUND));

        return HqTaxDetailResponse.from(ntsTax);
    }

    public HqSearchCsNameResponseList searchCsName(String keyword) {
        List<HqSearchCsNameResponse> responseList = teamRepository.findByNameContaining(keyword).stream()
                .map(HqSearchCsNameResponse::from)
                .toList();

        return HqSearchCsNameResponseList.from(responseList);
    }

    public HqSearchCsResponseList searchCs(String keyword) {
        List<Team> teamList = teamRepository.findByNameContaining(keyword);
        List<HqSearchCsResponse> responseList = userRepository.findByTeamInAndIsDeleted(teamList).stream()
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
