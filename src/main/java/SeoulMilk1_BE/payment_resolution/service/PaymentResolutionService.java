package SeoulMilk1_BE.payment_resolution.service;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import SeoulMilk1_BE.nts_tax.service.NtsTaxService;
import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.payment_resolution.dto.request.PaymentResolutionRequest;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionInsertResponse;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionListResponse;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionReadResponse;
import SeoulMilk1_BE.payment_resolution.repository.PaymentResolutionRepository;
import SeoulMilk1_BE.payment_resolution.utils.PaymentResolutionConstants;
import SeoulMilk1_BE.user.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentResolutionService {

    private final PaymentResolutionRepository paymentResolutionRepository;
    private final TeamService teamService;
    private final NtsTaxService ntsTaxService;

    @Transactional
    public PaymentResolutionInsertResponse createPaymentResolution(PaymentResolutionRequest request) {
        Optional<Integer> countResult = teamService.findAboutPayment(request.paymentRecipient(), LocalDateTime.now().getMonthValue());
        int count = 1;
        if (countResult != null) {
            count = countResult.get();
        }
        PaymentResolution paymentResolution = PaymentResolutionReadResponse.of(count, request);
        paymentResolutionRepository.save(paymentResolution);

        ntsTaxService.updatePaymentWritten(request.paymentDetails().get(0).getNtsTaxNum());

        return PaymentResolutionInsertResponse.of(paymentResolution.getId(), paymentResolution.getModifiedAt());
    }

    public PaymentResolutionReadResponse readPaymentResolution(Long id) {
        PaymentResolution paymentResolution = paymentResolutionRepository.findById(id).orElseThrow(() -> new GeneralException(ErrorStatus.PAYMENT_RESOLUTION_NOT_FOUND));
        return PaymentResolutionReadResponse.byId(paymentResolution);
    }

    @Transactional
    public PaymentResolutionInsertResponse updatePaymentResolution(Long id, PaymentResolutionReadResponse request) {
        PaymentResolution paymentResolution = paymentResolutionRepository.findById(id).orElseThrow(() -> new GeneralException(ErrorStatus.PAYMENT_RESOLUTION_NOT_FOUND));
        paymentResolution.updatePaymentResolution(request);
        return PaymentResolutionInsertResponse.of(paymentResolution.getId(), paymentResolution.getModifiedAt());
    }

    @Transactional
    public String deletePaymentResolution(Long id) {
        paymentResolutionRepository.deleteById(id);
        return PaymentResolutionConstants.DELETE_SUCCESS.getMessage();
    }

    public List<PaymentResolutionListResponse> readPaymentResolutionList(int period, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<PaymentResolution> result = paymentResolutionRepository.findListByDeadline(LocalDateTime.now().minusMonths(period), pageRequest).getContent();
        return result.stream().map(p -> PaymentResolutionListResponse.from(p)).collect(Collectors.toList());
    }
}
