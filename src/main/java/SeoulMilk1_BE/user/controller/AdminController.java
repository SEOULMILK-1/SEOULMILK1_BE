package SeoulMilk1_BE.user.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.user.service.UserService;
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

    private final UserService userService;

    @Operation(summary = "승인 대기중인 본사/대리점 사용자 조회", description = "승인 대기중인 본사/대리점 사용자 조회")
    @GetMapping("/pending")
    public ApiResponse<?> findPendingUsers() {
        return ApiResponse.onSuccess(userService.findPendingUsers());
    }

    @Operation(summary = "승인 대기중인 사용자 승인", description = "승인 대기중인 본사/대리점 사용자 승인")
    @PostMapping("/approve/{userId}")
    public ApiResponse<?> approvePendingUser(@PathVariable Long userId) {
        return ApiResponse.onSuccess(userService.approvePendingUser(userId));
    }

    @Operation(summary = "승인 대기중인 사용자 거절", description = "승인 대기중인 본사/대리점 사용자 거절")
    @PostMapping("/reject/{userId}")
    public ApiResponse<?> rejectPendingUser(@PathVariable Long userId) {
        return ApiResponse.onSuccess(userService.rejectPendingUser(userId));
    }
}
