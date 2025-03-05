package SeoulMilk1_BE.payment_resolution.service;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.payment_resolution.dto.request.PaymentResolutionRequest;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionReadResponse;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionInsertResponse;
import SeoulMilk1_BE.payment_resolution.repository.PaymentResolutionRepository;
import SeoulMilk1_BE.payment_resolution.utils.PaymentResolutionConstants;
import SeoulMilk1_BE.user.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentResolutionService {
    private final PaymentResolutionRepository paymentResolutionRepository;
    private final TeamService teamService;

    @Transactional
    public PaymentResolutionInsertResponse createPaymentResolution(PaymentResolutionRequest request) {
        Optional<Integer> countResult = teamService.findAboutPayment(request.paymentRecipient(), LocalDateTime.now().getMonthValue());
        int count = 1;
        if (countResult != null) {
            count = countResult.get();
        }
        PaymentResolution paymentResolution = PaymentResolutionReadResponse.of(count, request);
        paymentResolutionRepository.save(paymentResolution);
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
}
