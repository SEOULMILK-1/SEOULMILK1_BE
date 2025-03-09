package SeoulMilk1_BE.payment_resolution.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.service.NtsTaxService;
import SeoulMilk1_BE.payment_resolution.dto.request.PaymentResolutionRequest;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionListResponse;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionReadResponse;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionInsertResponse;
import SeoulMilk1_BE.payment_resolution.service.PaymentResolutionService;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payment_Resolution", description = "지급결의서 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/hq/payment-resolution")
public class PaymentResolutionController {
    private final PaymentResolutionService paymentResolutionService;
    private final NtsTaxService ntsTaxService;
    private final UserService userService;

    @Operation(summary = "자동 작성된 지급결의서 조회", description = "지급결의서 작성 희망하는 기간 값을 넣어주세요!! (1 -> 최근 1달치, 3 -> 최근 3달치")
    @GetMapping("/create/{period}")
    public ApiResponse<PaymentResolutionRequest> getAutoResult(@PathVariable("period") int period, @AuthenticationPrincipal Long userId) {
        List<NtsTax> ntsTaxList = ntsTaxService.findByPeriod(period);
        User user = userService.findUser(userId);
        return ApiResponse.onSuccess(PaymentResolutionRequest.from(ntsTaxList, user));
    }

    @Operation(summary = "지급결의서 생성", description = "자동 작성된 값들 토대로 넣어주세요!!")
    @PostMapping
    public ApiResponse<PaymentResolutionInsertResponse> createPaymentResolution(@RequestBody PaymentResolutionRequest request) {
        return ApiResponse.onSuccess(paymentResolutionService.createPaymentResolution(request));
    }

    @Operation(summary = "지급결의서 수정", description = "수정 희망하는 지급결의서 ID와 수정된 값 포함된 전체 값을 넣어주세요!")
    @PutMapping("/{payment_resolution_id}")
    public ApiResponse<PaymentResolutionInsertResponse> updatePaymentResolution(@PathVariable("payment_resolution_id") Long id, @RequestBody PaymentResolutionReadResponse request) {
        return ApiResponse.onSuccess(paymentResolutionService.updatePaymentResolution(id, request));
    }

    @Operation(summary = "지급결의서 삭제", description = "삭제 희망하는 지급결의서 ID 넣어주세요!")
    @DeleteMapping("/{payment_resolution_id}")
    public ApiResponse<String> deletePaymentResolution(@PathVariable("payment_resolution_id") Long id) {
        return ApiResponse.onSuccess(paymentResolutionService.deletePaymentResolution(id));
    }

    @Operation(summary = "지급결의서 조회", description = "조회 희망하는 지급결의서 ID 넣어주세요!")
    @GetMapping("/{payment_resolution_id}")
    public ApiResponse<PaymentResolutionReadResponse> readPaymentResolution(@PathVariable("payment_resolution_id") Long id) {
        return ApiResponse.onSuccess(paymentResolutionService.readPaymentResolution(id));
    }

    @Operation(summary = "지급결의서 목록 조회", description = "period(path variable): 조회 희망하는 기간 (1 -> 최근 1달치, 3 -> 최근 3달치) <br>" +
            "page: 조회할 페이지 번호" +
            "size: 한 페이지에 조회할 사용자 수")
    @GetMapping("/list/{period}")
    public ApiResponse<List<PaymentResolutionListResponse>> readPaymentResolutionList(@PathVariable("period") int period,
                                                                                     @RequestParam(defaultValue = "0") int page,
                                                                                     @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.onSuccess(paymentResolutionService.readPaymentResolutionList(period, page, size));
    }

    @Operation(summary = "지급결의서 pdf 파일 다운로드", description = "다운로드 받을 지급결의서 id를 보내주세요")
    @GetMapping(value = "/pdf/{payment_resolution_id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ApiResponse<byte[]> createPdfFile(@PathVariable("payment_resolution_id") Long paymentResolutionId) {
        return ApiResponse.onSuccess(paymentResolutionService.createPdfFile(paymentResolutionId));
    }
}
