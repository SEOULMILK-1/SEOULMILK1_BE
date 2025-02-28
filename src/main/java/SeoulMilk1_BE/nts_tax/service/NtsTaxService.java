package SeoulMilk1_BE.nts_tax.service;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.exception.NtsTaxNotFoundException;
import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepository;
import SeoulMilk1_BE.nts_tax.dto.response.OcrApiResponse;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.TAX_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NtsTaxService {

    private final NtsTaxRepository ntsTaxRepository;
    private final UserService userService;

    @Transactional
    public NtsTax saveNtsTax(OcrApiResponse ocrApiResponse, Long userId, String imageUrl) {
        User user = userService.findUser(userId);

        NtsTax ntsTax = NtsTax.toNtsTax(ocrApiResponse, user, imageUrl);
        ntsTaxRepository.save(ntsTax);

        return findByIssueId(ntsTax.getIssueId());
    }

    public NtsTax findByIssueId(String issueId) {
        return ntsTaxRepository.findByIssueId(issueId)
                .orElseThrow(() -> new NtsTaxNotFoundException(TAX_NOT_FOUND));
    }
}
