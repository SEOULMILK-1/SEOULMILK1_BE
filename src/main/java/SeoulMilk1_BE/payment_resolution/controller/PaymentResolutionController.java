package SeoulMilk1_BE.payment_resolution.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.dto.response.HqSearchTaxResponseList;
import SeoulMilk1_BE.nts_tax.service.NtsTaxService;
import SeoulMilk1_BE.payment_resolution.dto.request.PaymentResolutionRequest;
import SeoulMilk1_BE.payment_resolution.dto.request.PaymentResolutionUpdateAccountRequest;
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

    @Operation(summary = "지급결의서 생성", description = "자동 작성된 값들 토대로 넣어주세요!!")
    @PostMapping
    public ApiResponse<String> createPaymentResolution(@AuthenticationPrincipal Long userId) {
        return ApiResponse.onSuccess(paymentResolutionService.createPaymentResolutionByGrouping(userId));
    }

    @Operation(summary = "지급 계좌 수정", description = "변경할 지급 계좌 번호를 넣어주세요!")
    @PatchMapping()
    public ApiResponse<String> updateAccountPaymentResolution(@RequestBody PaymentResolutionUpdateAccountRequest request) {
        return ApiResponse.onSuccess(paymentResolutionService.updateAccount(request));
    }

    @Operation(summary = "지급결의서 삭제", description = "삭제 희망하는 지급결의서 ID 넣어주세요!")
    @DeleteMapping("/{payment_resolution_id}")
    public ApiResponse<String> deletePaymentResolution(@PathVariable("payment_resolution_id") Long id) {
        return ApiResponse.onSuccess(paymentResolutionService.deletePaymentResolution(id));
    }

    @Operation(summary = "지급결의서 상세 조회", description = "조회 희망하는 지급결의서 ID 넣어주세요!")
    @GetMapping("/{payment_resolution_id}")
    public ApiResponse<PaymentResolutionReadResponse> readPaymentResolution(@PathVariable("payment_resolution_id") Long id) {
        return ApiResponse.onSuccess(paymentResolutionService.readPaymentResolution(id));
    }

    @Operation(summary = "지급결의서 목록 조회", description = "검색 조건을 설정하지 않으면 지급결의서 전체 목록이 조회됩니다.<br><br>" +
            "suDeptName : 대리점 이름을 입력해주세요. <br>" +
            "months : 기간(ex. 1개월, 3개월, 6개월 등)에 사용된 숫자를 입력해주세요. <br>" +
            "startDate, endDate: 시작 / 끝 날짜를 입력해주세요. <br><br>" +
            "page : 조회할 페이지 번호 <br> size : 한 페이지에 조회할 세금계산서 수")
    @GetMapping("/list")
    public ApiResponse<List<PaymentResolutionListResponse>> readPaymentResolutionList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String suDeptName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer months) {
        return ApiResponse.onSuccess(paymentResolutionService.readPaymentResolutionList(page, size, suDeptName, startDate, endDate, months));
    }

    @Operation(summary = "작성된 지급결의서 목록 조회", description = "지급결의서 일괄 작성 후 조회되는 지급결의서 전체 목록 조회 API 입니다.")
    @GetMapping("/list/all")
    public ApiResponse<List<PaymentResolutionListResponse>> readPaymentResolutionList() {
        return ApiResponse.onSuccess(paymentResolutionService.readAllPaymentResolutions());
    }

    @Operation(summary = "지급결의서 pdf 파일 다운로드", description = "다운로드 받을 지급결의서 id를 보내주세요")
    @GetMapping(value = "/pdf/{payment_resolution_id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] createPdfFile(@PathVariable("payment_resolution_id") Long paymentResolutionId) {
        return paymentResolutionService.createPdfFile(paymentResolutionId);
    }
}
