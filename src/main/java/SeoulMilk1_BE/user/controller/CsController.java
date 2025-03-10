package SeoulMilk1_BE.user.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.nts_tax.domain.type.ValidStatus;
import SeoulMilk1_BE.nts_tax.dto.response.CsApprovedTaxResponseList;
import SeoulMilk1_BE.nts_tax.dto.response.CsRefusedTaxResponseList;
import SeoulMilk1_BE.nts_tax.dto.response.CsSearchTaxResponseList;
import SeoulMilk1_BE.nts_tax.dto.response.CsTaxDetailResponse;
import SeoulMilk1_BE.user.service.CsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CS", description = "대리점 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cs")
public class CsController {

    private final CsService csService;

    @Operation(summary = "이번 달 반려된 세금계산서 조회", description = "이번 달 반려된 세금계산서 목록을 조회합니다.")
    @GetMapping("/tax/refuse")
    public ApiResponse<CsRefusedTaxResponseList> getThisMonthRefusedTax(@AuthenticationPrincipal Long userId) {
        return ApiResponse.onSuccess(csService.getThisMonthRefusedTax(userId));
    }

    @Operation(summary = "이번 달 승인된 세금계산서 조회", description = "이번 달 승인된 세금계산서 목록을 조회합니다.")
    @GetMapping("/tax/approve")
    public ApiResponse<CsApprovedTaxResponseList> getThisMonthApprovedTax(@AuthenticationPrincipal Long userId) {
        return ApiResponse.onSuccess(csService.getThisMonthApprovedTax(userId));
    }

    @Operation(summary = "세금계산서 검색", description = "검색 조건을 설정하지 않으면 세금계산서 전체 목록이 조회됩니다.<br><br>" +
            "months : 기간(ex. 1개월, 3개월, 6개월 등)에 사용된 숫자를 입력해주세요 <br><br>" +
            "page : 조회할 페이지 번호 <br> size : 한 페이지에 조회할 세금계산서 수")
    @GetMapping("/search/tax")
    public ApiResponse<CsSearchTaxResponseList> searchTax(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long months,
            @RequestParam(required = false) ValidStatus status) {
        return ApiResponse.onSuccess(csService.searchTax(userId, page, size, startDate, endDate, months, status));
    }

    @Operation(summary = "세금계산서 상세 조회", description = "상세 조회할 세금계산서의 ID 값을 넣어주세요")
    @GetMapping("/tax/{ntsTaxId}")
    public ApiResponse<CsTaxDetailResponse> getTaxDetail(@PathVariable Long ntsTaxId) {
        return ApiResponse.onSuccess(csService.getTaxDetail(ntsTaxId));
    }
}
