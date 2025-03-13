package SeoulMilk1_BE.user.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.nts_tax.dto.response.AdminSearchTaxResponseList;
import SeoulMilk1_BE.nts_tax.dto.response.AdminTaxDetailResponse;
import SeoulMilk1_BE.user.dto.response.PendingUserResponseList;
import SeoulMilk1_BE.user.dto.response.UserManageResponseList;
import SeoulMilk1_BE.user.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin", description = "관리자 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "등록 대기중인 본사/대리점 사용자 조회", description = "등록 대기중인 본사/대리점 사용자 조회 <br><br> " +
            "page : 조회할 페이지 번호 <br> " +
            "size : 한 페이지에 조회할 사용자 수")
    @GetMapping("/pending")
    public ApiResponse<PendingUserResponseList> findPendingUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(adminService.findPendingUsers(page, size));
    }

    @Operation(summary = "본사/대리점 유저 조회", description = "본사/대리점 사용자 전체 조회 <br><br> " +
            "page : 조회할 페이지 번호 <br> " +
            "size : 한 페이지에 조회할 사용자 수")
    @GetMapping("/user")
    public ApiResponse<UserManageResponseList> findAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.onSuccess(adminService.findAllUsers(page, size));
    }

    @Operation(summary = "승인 대기중인 사용자 승인", description = "승인 대기중인 본사/대리점 사용자 승인")
    @PostMapping("/approve/{userId}")
    public ApiResponse<?> approvePendingUser(@PathVariable Long userId) {
        return ApiResponse.onSuccess(adminService.approvePendingUser(userId));
    }

    @Operation(summary = "회원 삭제", description = "회원을 삭제하는 기능")
    @DeleteMapping("/{userId}")
    public ApiResponse<?> deleteUser(@PathVariable Long userId) {
        return ApiResponse.onSuccess(adminService.deleteUser(userId));
    }

    @Operation(summary = "세금계산서 검색", description = "검색 조건을 설정하지 않으면 세금계산서 전체 목록이 조회됩니다.<br><br>" +
            "keyword : 대리점 검색에 사용된 키워드를 입력해주세요 <br>" +
            "months : 기간(ex. 1개월, 3개월, 6개월 등)에 사용된 숫자를 입력해주세요 <br><br>" +
            "page : 조회할 페이지 번호 <br> size : 한 페이지에 조회할 세금계산서 수")
    @GetMapping("/search/tax")
    public ApiResponse<AdminSearchTaxResponseList> searchTax(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long months,
            @RequestParam(required = false) Boolean status) {
        return ApiResponse.onSuccess(adminService.searchTax(page, size, keyword, startDate, endDate, months, status));
    }

    @Operation(summary = "세금계산서 상세 조회", description = "상세 조회할 세금계산서의 ID 값을 넣어주세요")
    @GetMapping("/tax/{ntsTaxId}")
    public ApiResponse<AdminTaxDetailResponse> getTaxDetail(@PathVariable Long ntsTaxId) {
        return ApiResponse.onSuccess(adminService.getTaxDetail(ntsTaxId));
    }
}
