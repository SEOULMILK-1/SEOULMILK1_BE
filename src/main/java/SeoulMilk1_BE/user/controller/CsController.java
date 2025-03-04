package SeoulMilk1_BE.user.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.nts_tax.domain.type.Status;
import SeoulMilk1_BE.nts_tax.dto.response.CsSearchTaxResponseList;
import SeoulMilk1_BE.user.service.CsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CS", description = "대리점 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cs")
public class CsController {

    private final CsService csService;

    @Operation(summary = "세금계산서 검색", description = "months : 기간(ex. 1개월, 3개월, 6개월 등)에 사용된 숫자를 입력해주세요 <br><br>" +
            "page : 조회할 페이지 번호 <br> size : 한 페이지에 조회할 세금계산서 수")
    @GetMapping("/search/tax")
    public ApiResponse<CsSearchTaxResponseList> searchTax(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long months,
            @RequestParam(required = false) Status status) {
        return ApiResponse.onSuccess(csService.searchTax(userId, page, size, startDate, endDate, months, status));
    }
}
