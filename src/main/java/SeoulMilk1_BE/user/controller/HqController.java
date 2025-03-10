package SeoulMilk1_BE.user.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.nts_tax.dto.response.HqSearchTaxResponseList;
import SeoulMilk1_BE.nts_tax.dto.response.HqTaxDetailResponse;
import SeoulMilk1_BE.nts_tax.dto.response.HqTaxResponseList;
import SeoulMilk1_BE.user.dto.request.HqAddManageCsRequest;
import SeoulMilk1_BE.user.dto.response.HqManageCsResponseList;
import SeoulMilk1_BE.user.dto.response.HqSearchCsNameResponseList;
import SeoulMilk1_BE.user.dto.response.HqSearchCsResponseList;
import SeoulMilk1_BE.user.service.HqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "HQ", description = "본사 직원 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/hq")
public class HqController {

    private final HqService hqService;

    @Operation(summary = "담당 대리점 조회", description = "직원이 담당하는 대리점 목록을 조회합니다.")
    @GetMapping("/manage/cs")
    public ApiResponse<HqManageCsResponseList> manageCs(@AuthenticationPrincipal Long userId) {
        return ApiResponse.onSuccess(hqService.getManageCsList(userId));
    }

    @Operation(summary = "담당 대리점 추가", description = "담당 대리점 추가 API 입니다.")
    @PostMapping("/manage/cs")
    public ApiResponse<String> addManageCs(
            @AuthenticationPrincipal Long userId,
            @RequestBody HqAddManageCsRequest request) {
        return ApiResponse.onSuccess(hqService.addManageCs(userId, request));
    }

    @Operation(summary = "지급 대기 세금계산서 조회", description = "담당 대리점의 지급 대기 세금계산서 목록을 조회합니다.")
    @GetMapping("/wait/tax")
    public ApiResponse<HqTaxResponseList> getTaxInfo(@AuthenticationPrincipal Long userId) {
        return ApiResponse.onSuccess(hqService.getTaxInfo(userId));
    }

    @Operation(summary = "세금계산서 검색", description = "검색 조건을 설정하지 않으면 세금계산서 전체 목록이 조회됩니다.<br><br>" +
            "keyword : 대리점 검색에 사용된 키워드를 입력해주세요 <br>" +
            "months : 기간(ex. 1개월, 3개월, 6개월 등)에 사용된 숫자를 입력해주세요 <br><br>" +
            "page : 조회할 페이지 번호 <br> size : 한 페이지에 조회할 세금계산서 수")
    @GetMapping("/search/tax")
    public ApiResponse<HqSearchTaxResponseList> searchTax(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long months,
            @RequestParam(required = false) Boolean status) {
        return ApiResponse.onSuccess(hqService.searchTax(page, size, keyword, startDate, endDate, months, status, userId));
    }

    @Operation(summary = "세금계산서 상세 조회", description = "상세 조회할 세금계산서의 ID 값을 넣어주세요")
    @GetMapping("/tax/{ntsTaxId}")
    public ApiResponse<HqTaxDetailResponse> getTaxDetail(@PathVariable Long ntsTaxId) {
        return ApiResponse.onSuccess(hqService.getTaxDetail(ntsTaxId));
    }

    @Operation(summary = "대리점명 조회", description = "대리점 검색에 사용되는 키워드를 입력해주세요 <br>" +
            "키워드를 포함하는 모든 대리점명이 제공됩니다")
    @GetMapping("/search/cs/name")
    public ApiResponse<HqSearchCsNameResponseList> searchCs(@RequestParam(required = false) String keyword) {
        return ApiResponse.onSuccess(hqService.searchCsName(keyword));
    }

    @Operation(summary = "대리점 직원 조회", description = "키워드를 포함하는 대리점에 소속된 모든 직원이 제공됩니다 <br><br>" +
            "page : 조회할 페이지 번호 <br> size : 한 페이지에 조회할 세금계산서 수")
    @GetMapping("/search/cs/info")
    public ApiResponse<HqSearchCsResponseList> searchCsInfo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        return ApiResponse.onSuccess(hqService.searchCs(page, size, keyword));
    }
}
