package SeoulMilk1_BE.payment_resolution.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.service.NtsTaxService;
import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.payment_resolution.dto.request.PaymentResolutionDto;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionInsertResponse;
import SeoulMilk1_BE.payment_resolution.service.PaymentResolutionService;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hq/payment-resolution")
public class PaymentResolutionController {
    private final PaymentResolutionService paymentResolutionService;
    private final NtsTaxService ntsTaxService;
    private final UserService userService;

    @GetMapping("/create/{period}")
    public ApiResponse<PaymentResolutionDto> getAutoResult(@PathVariable("period") int period, @AuthenticationPrincipal Long userId) {
        List<NtsTax> ntsTaxList = ntsTaxService.findByPeriod(period);
        User user = userService.findUser(userId);
        return ApiResponse.onSuccess(PaymentResolutionDto.from(ntsTaxList, user));
    }

    @PostMapping
    public ApiResponse<PaymentResolutionInsertResponse> createPaymentResolution(@RequestBody PaymentResolutionDto request) {
        return ApiResponse.onSuccess(paymentResolutionService.createPaymentResolution(request));
    }

    @PutMapping("/{payment_resolution_id}")
    public ApiResponse<PaymentResolutionInsertResponse> updatePaymentResolution(@PathVariable("payment_resolution_id") Long id, @RequestBody PaymentResolutionDto request) {
        return ApiResponse.onSuccess(paymentResolutionService.updatePaymentResolution(id, request));
    }

    @DeleteMapping("/{payment_resolution_id}")
    public ApiResponse<String> deletePaymentResolution(@PathVariable("payment_resolution_id") Long id) {
        return ApiResponse.onSuccess(paymentResolutionService.deletePaymentResolution(id));
    }

    @GetMapping("/{payment_resolution_id}")
    public ApiResponse<PaymentResolutionDto> readPaymentResolution(@PathVariable("payment_resolution_id") Long id) {
        return ApiResponse.onSuccess(paymentResolutionService.readPaymentResolution(id));
    }


}
