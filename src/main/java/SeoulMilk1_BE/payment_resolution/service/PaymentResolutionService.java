package SeoulMilk1_BE.payment_resolution.service;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.payment_resolution.dto.request.PaymentResolutionDto;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionInsertResponse;
import SeoulMilk1_BE.payment_resolution.repository.PaymentResolutionRepository;
import SeoulMilk1_BE.payment_resolution.utils.PaymentResolutionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentResolutionService {
    private final PaymentResolutionRepository paymentResolutionRepository;

    @Transactional
    public PaymentResolutionInsertResponse createPaymentResolution(PaymentResolutionDto request) {
        PaymentResolution paymentResolution = PaymentResolutionDto.of(request);
        paymentResolutionRepository.save(paymentResolution);
        return PaymentResolutionInsertResponse.of(paymentResolution.getId(), paymentResolution.getModifiedAt());
    }

    public PaymentResolutionDto readPaymentResolution(Long id) {
        PaymentResolution paymentResolution = paymentResolutionRepository.findById(id).orElseThrow(() -> new GeneralException(ErrorStatus.PAYMENT_RESOLUTION_NOT_FOUND));
        return PaymentResolutionDto.byId(paymentResolution);
    }

    @Transactional
    public PaymentResolutionInsertResponse updatePaymentResolution(Long id, PaymentResolutionDto request) {
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
