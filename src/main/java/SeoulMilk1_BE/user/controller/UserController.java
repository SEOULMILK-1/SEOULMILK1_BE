package SeoulMilk1_BE.user.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.user.dto.request.UpdateUserRequest;
import SeoulMilk1_BE.user.dto.response.UserDetailResponse;
import SeoulMilk1_BE.user.dto.response.UserResponse;
import SeoulMilk1_BE.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "유저 관련 공통 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 조회", description = "홈 화면의 사이드바 유저 정보 조회 (관리자, 본사 직원, 대리점 직원 공통 사용)")
    @GetMapping("")
    public ApiResponse<UserResponse> getUserInfo(@AuthenticationPrincipal Long userId) {
        return ApiResponse.onSuccess(userService.getUserInfo(userId));
    }

    @Operation(summary = "유저 상세 정보 조회", description = "유저 상세 정보 조회 (관리자, 본사 직원, 대리점 직원 공통 사용)")
    @GetMapping("/detail")
    public ApiResponse<UserDetailResponse> getUserDetail(@AuthenticationPrincipal Long userId) {
        return ApiResponse.onSuccess(userService.getUserDetail(userId));
    }

    @Operation(summary = "유저 정보 수정", description = "유저 정보 수정 (본사 직원, 대리점 직원 공통 사용)")
    @PutMapping("/update")
    public ApiResponse<String> updateUser(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody UpdateUserRequest request) {
        return ApiResponse.onSuccess(userService.updateUser(userId, request));
    }
}
