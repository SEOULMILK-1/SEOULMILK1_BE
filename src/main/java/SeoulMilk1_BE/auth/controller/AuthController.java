package SeoulMilk1_BE.auth.controller;

import SeoulMilk1_BE.auth.dto.request.LoginRequest;
import SeoulMilk1_BE.auth.dto.request.SignUpRequest;
import SeoulMilk1_BE.auth.dto.response.LoginResponse;
import SeoulMilk1_BE.auth.service.AuthService;
import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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

    @Operation(summary = "회원가입", description = "ROLE에 관리자라면 ADMIN, 본사 사용자라면 HQ_USER, 고객센터 사용자라면 AGENCY_USER를 입력해주세요.")
    @PostMapping("/sign-up")
    public ApiResponse<?> signUp(@Valid @RequestBody SignUpRequest request) {
        return ApiResponse.onSuccess(authService.signUp(request));
    }

    @Operation(summary = "로그인", description = "access token은 header에 Authorization: Bearer 로, refresh token은 쿠키로 전달됩니다. <br><br>" +
            "이후 user의 role에 맞춰 redirect를 해주시면 됩니다.")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        return ApiResponse.onSuccess(authService.login(loginRequest, response));
    }
}