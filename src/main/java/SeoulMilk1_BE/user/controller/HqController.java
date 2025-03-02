package SeoulMilk1_BE.user.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.nts_tax.dto.response.HqTaxResponseList;
import SeoulMilk1_BE.user.dto.response.HqSearchCsNameResponseList;
import SeoulMilk1_BE.user.dto.response.HqSearchCsResponseList;
import SeoulMilk1_BE.user.service.HqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "HQ", description = "본사 직원 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/hq")
public class HqController {

    private final HqService hqService;

    @Operation(summary = "이번 달 세금계산서 조회", description = "본사 직원의 홈 화면입니다. <br><br>" +
            "page : 조회할 페이지 번호 <br> size : 한 페이지에 조회할 사용자 수")
    @GetMapping
    public ApiResponse<HqTaxResponseList> getTaxInfo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.onSuccess(hqService.getTaxInfo(page, size));
    }

    @Operation(summary = "세금계산서 검색", description = "keyword : 고객센터 검색에 사용된 키워드를 입력해주세요 <br>" +
            "months : 기간(ex. 1개월, 3개월, 6개월 등)에 사용된 숫자를 입력해주세요 <br><br>" +
            "page : 조회할 페이지 번호 <br> size : 한 페이지에 조회할 세금계산서 수")
    @GetMapping("/search/tax")
    public ApiResponse<HqTaxResponseList> searchTax(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long months) {
        return ApiResponse.onSuccess(hqService.searchTax(page, size, keyword, startDate, endDate, months));
    }

    @Operation(summary = "고객센터명 조회", description = "고객센터 지점 검색에 사용되는 키워드를 입력해주세요 <br>" +
            "키워드를 포함하는 모든 고객센터 지점명이 제공됩니다")
    @GetMapping("/search/cs/name")
    public ApiResponse<HqSearchCsNameResponseList> searchCs(@RequestParam(required = false) String keyword) {
        return ApiResponse.onSuccess(hqService.searchCsName(keyword));
    }

    @Operation(summary = "고객센터 지점 조회", description = "키워드를 포함하는 고객센터 검색에 사용되는 API입니다 <br>" +
            "고객센터 정보들이 제공됩니다")
    @GetMapping("/search/cs/info")
    public ApiResponse<HqSearchCsResponseList> searchCsInfo(@RequestParam(required = false) String keyword) {
        return ApiResponse.onSuccess(hqService.searchCs(keyword));
    }
}
