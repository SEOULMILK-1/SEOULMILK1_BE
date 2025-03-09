package SeoulMilk1_BE.nts_tax.service;

import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import SeoulMilk1_BE.global.service.S3Service;
import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.dto.request.CodefApiRequest;
import SeoulMilk1_BE.nts_tax.dto.request.UpdateTaxRequest;
import SeoulMilk1_BE.nts_tax.dto.response.IssueIdTaxResponse;
import SeoulMilk1_BE.nts_tax.exception.NtsTaxNotFoundException;
import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepository;
import SeoulMilk1_BE.nts_tax.dto.response.OcrApiResponse;
import SeoulMilk1_BE.payment_resolution.domain.PaymentDetails;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.TAX_NOT_FOUND;
import static SeoulMilk1_BE.nts_tax.util.TaxConstants.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NtsTaxService {

    private final NtsTaxRepository ntsTaxRepository;
    private final UserService userService;
    private final CodefService codefService;
    private final S3Service s3Service;

    @Transactional
    public IssueIdTaxResponse saveNtsTax(OcrApiResponse ocrApiResponse, Long userId, String imageUrl) {
        User user = userService.findUser(userId);
        NtsTax ocrNtsTax = NtsTax.toNtsTax(ocrApiResponse, user, imageUrl);

        if (ntsTaxRepository.existsByIssueId(ocrNtsTax.getIssueId())) {
            return IssueIdTaxResponse.of(true, findByIssueId(ocrNtsTax.getIssueId()));
        } else {
            ntsTaxRepository.save(ocrNtsTax);
            NtsTax findNtsTax = findByIssueId(ocrNtsTax.getIssueId());

            String issueYearMonth = findNtsTax.getIssueDate().substring(0, 6);
            Long count = ntsTaxRepository.countByTeamAndIssueYearMonth(user.getTeam(), issueYearMonth);

            findNtsTax.updateTitle(count);

            return IssueIdTaxResponse.of(false, findNtsTax);
        }
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
        s3Service.deleteImageFromS3(ntsTax.getTaxImgUrl());
        ntsTaxRepository.delete(ntsTax);

        return DELETE_SUCCESS.getMessage();
    }

    @Transactional
    public String validateNtsTax(Long ntsTaxId) {
        NtsTax ntsTax = ntsTaxRepository.findById(ntsTaxId).orElseThrow(() -> new GeneralException(TAX_NOT_FOUND));
        CodefApiRequest request = CodefApiRequest.from(ntsTax);
        String result = codefService.validateNtsTax(request);

        if (result.equals("성공")) {
            ntsTax.updateStatus(0);
        } else {
            ntsTax.updateStatus(1);
        }

        return VALIDATE_SUCCESS.getMessage();
    }

    public List<NtsTax> findByPeriod(int period) {
        LocalDateTime deadline = LocalDateTime.now().minusMonths(period);
        List<NtsTax> ntsTaxList = ntsTaxRepository.findAllByPeriod(deadline);
        return ntsTaxList;
    }

    @Transactional
    public void updatePaymentWritten(List<PaymentDetails> paymentDetails) {
        paymentDetails.forEach(paymentDetail -> {
            String issueId = paymentDetail.getNtsTaxNum();
            NtsTax ntsTax = findByIssueId(issueId);
            ntsTax.updatePaymentWritten();
        });
    }

    public NtsTax findById(Long id) {
        return ntsTaxRepository.findById(id)
                .orElseThrow(() -> new NtsTaxNotFoundException(TAX_NOT_FOUND));
    }

    public NtsTax findByIssueId(String issueId) {
        return ntsTaxRepository.findByIssueId(issueId)
                .orElseThrow(() -> new NtsTaxNotFoundException(TAX_NOT_FOUND));
    }
}
