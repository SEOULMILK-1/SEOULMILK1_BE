package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.dto.response.HqTaxResponse;
import SeoulMilk1_BE.nts_tax.dto.response.HqTaxResponseList;
import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HqService {

    private final NtsTaxRepository ntsTaxRepository;

    public HqTaxResponseList getTaxInfo(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
//        String thisMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String thisMonth = "202406";

        Page<NtsTax> ntsTaxList = ntsTaxRepository.findAllByIssueDateStartsWith(thisMonth, pageable);

        List<HqTaxResponse> responseList = ntsTaxList.stream()
                .map(HqTaxResponse::from)
                .toList();

        return HqTaxResponseList.from(responseList);
    }
}
