package SeoulMilk1_BE.auth.controller;

import SeoulMilk1_BE.auth.dto.request.SignUpRequest;
import SeoulMilk1_BE.auth.service.AuthService;
import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Auth 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "테스트용 토큰발급", description = "테스트용 토큰발급")
    @GetMapping("/test/{userId}")
    public String testToken(@PathVariable Long userId) {
        return authService.getTestToken(userId);
    }

    @Operation(summary = "사번 중복 검사", description = "사번 중복 검사")
    @GetMapping("/validation/employee-id")
    public ApiResponse<?> validateEmployeeId(@Valid @RequestParam Long employeeId) {
        return ApiResponse.onSuccess(authService.validateEmployeeId(employeeId));
    }

    @Operation(summary = "회원가입", description = "ROLE에 관리자라면 ADMIN, 본사 사용자라면 HQ_USER, 대리점 사용자라면 AGENCY_USER를 입력해주세요.")
    @PostMapping("/sign-up")
    public ApiResponse<?> signUp(@Valid @RequestBody SignUpRequest request) {
        return ApiResponse.onSuccess(authService.signUp(request));
    }
}