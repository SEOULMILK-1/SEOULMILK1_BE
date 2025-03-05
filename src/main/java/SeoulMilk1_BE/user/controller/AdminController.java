package SeoulMilk1_BE.user.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
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

    @Operation(summary = "승인 대기중인 본사/대리점 사용자 조회", description = "승인 대기중인 본사/대리점 사용자 조회 <br><br> " +
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
}
