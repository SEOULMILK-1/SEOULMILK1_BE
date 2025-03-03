package SeoulMilk1_BE.nts_tax.service;

import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.dto.request.CodefApiRequest;
import SeoulMilk1_BE.nts_tax.dto.request.UpdateTaxRequest;
import SeoulMilk1_BE.nts_tax.exception.NtsTaxNotFoundException;
import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepository;
import SeoulMilk1_BE.nts_tax.dto.response.OcrApiResponse;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.TAX_NOT_FOUND;
import static SeoulMilk1_BE.nts_tax.util.TaxConstants.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NtsTaxService {

    private final NtsTaxRepository ntsTaxRepository;
    private final UserService userService;
    private final CodefService codefService;

    @Transactional
    public NtsTax saveNtsTax(OcrApiResponse ocrApiResponse, Long userId, String imageUrl) {
        User user = userService.findUser(userId);

        NtsTax ntsTax = NtsTax.toNtsTax(ocrApiResponse, user, imageUrl);
        ntsTaxRepository.save(ntsTax);

        return findByIssueId(ntsTax.getIssueId());
    }

    @Transactional
    public String updateNtsTax(Long ntsTaxId, UpdateTaxRequest request) {
        NtsTax ntsTax = findById(ntsTaxId);
        ntsTax.updateNtsTax(request);

        return UPDATE_SUCCESS.getMessage();
    }

    @Transactional
    public String deleteNtsTax(Long ntsTaxId) {
        NtsTax ntsTax = findById(ntsTaxId);
        ntsTaxRepository.delete(ntsTax);

        return DELETE_SUCCESS.getMessage();
    }

    public NtsTax findById(Long id) {
        return ntsTaxRepository.findById(id)
                .orElseThrow(() -> new NtsTaxNotFoundException(TAX_NOT_FOUND));
    }

    public NtsTax findByIssueId(String issueId) {
        return ntsTaxRepository.findByIssueId(issueId)
                .orElseThrow(() -> new NtsTaxNotFoundException(TAX_NOT_FOUND));
    }

    @Transactional
    public String validateNtsTax(Long ntsTaxId) {
        NtsTax ntsTax = ntsTaxRepository.findById(ntsTaxId).orElseThrow(() -> new GeneralException(TAX_NOT_FOUND));
        CodefApiRequest request = CodefApiRequest.of(ntsTax.getSuId(), ntsTax.getIpId(), ntsTax.getIssueId(), ntsTax.getChargeTotal(), ntsTax.getIssueDate());
        String result = codefService.validateNtsTax(request);

        if (result.equals("성공")) {
            ntsTax.updateStatus(0);
        } else {
            ntsTax.updateStatus(1);
        }

        return VALIDATE_SUCCESS.getMessage();
    }
}
