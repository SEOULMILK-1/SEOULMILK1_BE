package SeoulMilk1_BE.user.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.nts_tax.dto.response.HqTaxResponseList;
import SeoulMilk1_BE.user.service.HqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Hq", description = "본사 직원 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/hq")
public class HqController {

    private final HqService hqService;

    @Operation(summary = "이번 달 세금계산서 조회", description = "page : 조회할 페이지 번호 <br> size : 한 페이지에 조회할 사용자 수")
    @GetMapping("/tax")
    public ApiResponse<HqTaxResponseList> getTaxInfo(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.onSuccess(hqService.getTaxInfo(page, size));
    }
}
