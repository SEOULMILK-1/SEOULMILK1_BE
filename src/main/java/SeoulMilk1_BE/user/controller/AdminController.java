package SeoulMilk1_BE.user.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin", description = "관리자 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Operation(summary = "승인 대기중인 본사/대리점 사용자 조회", description = "승인 대기중인 본사/대리점 사용자 조회")
    @GetMapping("/pending")
    public ApiResponse<?> findPendingUsers(@AuthenticationPrincipal Long userId) {
        return ApiResponse.onSuccess(userService.findPendingUsers());
    }
}
